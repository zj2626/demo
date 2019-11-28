package com.demo.common.service.thread.volatilee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 　　第一：使用volatile关键字会强制将修改的值立即写入主存；
 * <p>
 * 　　第二：使用volatile关键字的话，当线程2进行修改时，会导致线程1的工作内存中缓存变量stop的缓存行无效（反映到硬件层的话，就是CPU的L1或者L2缓存中对应的缓存行无效）；
 * <p>
 * 　　第三：由于线程1的工作内存中缓存变量stop的缓存行无效，所以线程1再次读取变量stop的值时会去主存读取。
 * <p>
 * 使用场景
 * <p>
 * synchronized关键字是防止多个线程同时执行一段代码，那么就会很影响程序执行效率，而volatile关键字在某些情况下性能要优于synchronized，但是要注意volatile关键字是无法替代synchronized关键字的，因为volatile关键字无法保证操作的原子性。通常来说，使用volatile必须具备以下2个条件：
 * <p>
 * 　　1）对变量的写操作不依赖于当前值
 * <p>
 * 　　2）该变量没有包含在具有其他变量的不变式中
 * <p>
 * 　　实际上，这些条件表明，可以被写入 volatile 变量的这些有效值独立于任何程序的状态，包括变量的当前状态。
 * <p>
 * 　　事实上，我的理解就是上面的2个条件需要保证操作是原子性操作，才能保证使用volatile关键字的程序在并发时能够正确执行。
 *
 * 总结:
 * 1.对volatile变量的使用需要只能是原子操作,比如状态量的改变,读取(还必须是原子的操作)
 * 2.对volatile变量的使用 或者在同步的代码块中使用不需要原子操作 (比如Java中的双重检查)
 */
public class TestVolatileDemo implements Runnable {
    private volatile Integer sum = 0;

    public static void main(String[] args) throws InterruptedException {
        TestVolatileDemo rd = new TestVolatileDemo();

        // way one
        ExecutorService service = Executors.newFixedThreadPool(15);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureTasks.add(service.submit(rd));
        }
        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();

        System.out.println(rd.sum);
    }

    @Override
    public void run() {

        // option one
        try {

            /*情况一*/
            /*volatile关键字能保证，执行到语句3时，语句1和语句2必定是执行完毕了的，且语句1和语句2的执行结果对语句3、语句4、语句5是可见的*/
            //x、y为非volatile变量
            //flag为volatile变量
            int x = 2;        //语句1
            int y = 0;        //语句2
            boolean flag = true;  //语句3
            x = 4;         //语句4
            y = -1;       //语句5

            /*情况二*/
            /*公共*/
            Object context = null;
            boolean inited = false;
            /*有可能语句2会在语句1之前执行，那么久可能导致context还没被初始化，而线程2中就使用未初始化的context去进行操作，导致程序出错。
　　这里如果用volatile关键字对inited变量进行修饰，就不会出现这种问题了，因为当执行到语句2时，必定能保证context已经初始化完毕*/
            //线程1:
            context = loadContext();   //语句1
            inited = true;             //语句2

            //线程2:
            while (!inited) {
                sleep();
            }
            doSomethingwithconfig(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object loadContext() {
        // 加载生成context 进行初始化

        return null;
    }

    private void sleep() {

    }

    private void doSomethingwithconfig(Object context) {

    }
}

