package com.demo.common.service.thread.threadlocal;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import lombok.Data;
import org.junit.Test;

import java.util.UUID;

/**
 * @name: InheritableThreadLocalDemo
 * @description: InheritableThreadLocal
 * @author: zhangj
 * @create: 2020-04-20 19:56
 **/
public class InheritableThreadLocalDemo extends MyExcutor {

    private static InheritableThreadLocal<SqlConnection> threadLocal = new InheritableThreadLocal();

    @Test
    public void test() throws InterruptedException {
        threadLocal.set(new SqlConnection());
        System.out.println(Thread.currentThread().getName() + "       -start--  " + threadLocal.get());

        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);

        for (int i = 10; i < 30; i++) {
            Thread.sleep(100);
            //            System.out.println(Thread.currentThread().getName() + "            -get--  " + threadLocal.get());
            //            if (null != threadLocal.get()) {
            //                final String s = threadLocal.get().getConnAddress().substring(0, 5) + " M" + i;
            //                System.out.println(Thread.currentThread().getName() + "            -set--  " + s);
            //                threadLocal.get().setConnAddress(s);
            //            }
            System.out.println(Thread.currentThread().getName() + "            -get--  " + threadLocal.get());
            //            if (i == 15) {
            //                System.out.println(Thread.currentThread().getName() + "            -remove -------  ");
            //                threadLocal.remove();
            //            }
            //            if (i == 20) {
            //                System.out.println(Thread.currentThread().getName() + "            -set-- new ");
            //                threadLocal.set(new SqlConnection());
            //            }
        }
        excutorPool.futureGet();
        System.out.println(Thread.currentThread().getName() + "      final -get--  " + threadLocal.get());

    }

    @Override
    public Object doExcute() throws Exception {
        try {
            System.out.println(Thread.currentThread().getName() + " - 任务开始了 -" + threadLocal.get());
            optionOne();
            System.out.println(Thread.currentThread().getName() + " - 任务结束了 -" + threadLocal.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 每一个Thread对象都有一个ThreadLocalMap对象，这个ThreadLocalMap持有对象的引用
    // ThreadLocalMap以当前的threadlocal对象为key，以真正的存储对象为value。get时通过threadlocal实例就可以找到绑定在当前线程上的对象。
    // 每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来
    private void optionOne() {
        try {
            for (int i = 10; i < 25; i++) {
                Integer threadNumber = Integer.valueOf(Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1));
                // get得到null的时候会调用setInitialValue方法，从而调用initialValue方法，所以要set的值可以直接在initialValue中返回
                Thread.sleep(30);
                System.out.println(Thread.currentThread().getName() + "-get0--  " + threadLocal.get());

                Thread.sleep(30);
                System.out.println(Thread.currentThread().getName() + "-get1--  " + threadLocal.get());

                if (null != threadLocal.get()) {
                    // 不同线程直接get的对象是同一个对象 但是各自线程可以直接set为一个新的对象
                    final String s = threadLocal.get().getConnAddress().substring(0, 5) + " " + threadNumber + " T" + i + "   >   " + UUID.randomUUID().toString().substring(0, 5);
                    System.out.println(Thread.currentThread().getName() + "-set --  " + s);
                    threadLocal.get().setConnAddress(s);
                    //                    threadLocal.set(new SqlConnection());
                }

                Thread.sleep(30);
                System.out.println(Thread.currentThread().getName() + "-get2--  " + threadLocal.get());

                Thread.sleep(30);
                System.out.println(Thread.currentThread().getName() + "-get3--  " + threadLocal.get());

                //                if (1 == threadNumber && i == 20) {
                //                    System.out.println(Thread.currentThread().getName() + "         -remove -------  ");
                //                    threadLocal.remove();
                //                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Data
    private static class SqlConnection {

        public SqlConnection() {
        }

        private String connAddress = UUID.randomUUID().toString().substring(0, 5);

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("[");
            sb.append("\"connAddress\":\"")
                    .append(connAddress).append('\"');
            sb.append(']');
            return sb.toString();
        }
    }
}
