package com.demo.common.service.thread.status;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Java中线程的状态分为6种。
 * <p>
 * 1. 初始(NEW)：新创建了一个线程对象，但还没有调用start()方法。
 * 2. 运行(RUNNABLE)：Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”。
 * 线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。
 * 该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。
 * 就绪状态的线程在获得CPU时间片后变为运行中状态（running）。                       [notify(),notifyall(),LockSupport.unpark()]
 * 3. 阻塞(BLOCKED)：表示线程阻塞于锁。                                          [synchronized]
 * 4. 等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）。  [wait(),join(),park()]
 * 5. 超时等待(TIMED_WAITING)：该状态不同于WAITING，它可以在指定的时间后自行返回。   [wait(long),join(long),sleep(long),LockSupport.parkUntil(),LockSupport
 * .parkNanos()]
 * 6. 终止(TERMINATED)：表示该线程已经执行完毕。
 */
public class StatusDemo {
    @Test
    public void timeWiting() {
        Thread result = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "[time-waiting]");
        result.start();

        System.out.println(result.getState());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getState());
    }

    @Test
    public void waiting() {
        String flag = "lock";

        Thread result = new Thread(() -> {
            try {
                synchronized (flag) {
                    flag.wait();
                }
                System.out.println("DONE~~~~~");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "[waiting]");
        result.start();

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getState());
        synchronized (flag) {
            flag.notify();
        }
        System.out.println("getState");
        System.out.println(result.getState());
        System.out.println(result.getState());
        System.out.println(result.getState());
    }

    @Test
    public void block() {
        Thread result = new Thread(new BlockDemo(), "time-waiting-0");
        result.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getState());

        result = new Thread(new BlockDemo(), "block-1");
        result.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getState());
    }

    static class BlockDemo extends Thread {
        @Override
        public void run() {
            try {
                synchronized (BlockDemo.class) {
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
