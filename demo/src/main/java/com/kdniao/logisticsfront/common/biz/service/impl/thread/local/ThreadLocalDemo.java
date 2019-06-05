package com.kdniao.logisticsfront.common.biz.service.impl.thread.local;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadLocalDemo implements Runnable {
    // ThreadLocal的作用就是省的传参数 其保证每个线程中的某对象的独立--独享变量;(不像成员属性可能由于注入(共享)而导致线程安全问题), 可以理解为局部变量(形参)

    // ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景
    private static ThreadLocal<SqlConnection> threadLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            System.out.println(Thread.currentThread().getName() + " >>>> initialValue");
            return super.initialValue();
        }
    };

    @Test
    public void testA() {
        ThreadLocalDemo rd = new ThreadLocalDemo();

        // way one
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureTasks.add(service.submit(rd));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        for (int i = 0; i < 2; i++) {
//            futureTasks.add(service.submit(rd2));
//            Thread.sleep(1);
//        }

        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
//
//        try {
//            System.out.println("________________________");
//            Thread thread = new Thread(rd);
//            Thread thread2 = new Thread(rd);
//            thread.start();
//            thread2.start();
//            thread.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        rd.show();
    }

    @Override
    public void run() {

        optionOne();
//        optionCompare();

        show();
    }

    // 每一个Thread对象都有一个ThreadLocalMap对象，这个ThreadLocalMap持有对象的引用
    // ThreadLocalMap以当前的threadlocal对象为key，以真正的存储对象为value。get时通过threadlocal实例就可以找到绑定在当前线程上的对象。
    // 每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来
    private void optionOne() {
        try {
            System.out.println("start " + threadLocal.hashCode());

            if (null == ThreadLocalDemo.threadLocal.get()) {
                SqlConnection sqlConnection = new SqlConnection(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " -set-" + sqlConnection.toString());
                ThreadLocalDemo.threadLocal.set(sqlConnection);
            }

            for (int i = 0; i < 5; i++) {
                SqlConnection sqlConnection = ThreadLocalDemo.threadLocal.get();
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " -get--" + sqlConnection.toString());

                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " T " + i + "-" + sqlConnection.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            int a = (int) (1 + Math.random() * (10 - 1 + 1));
//            if (a % 2 == 0) {
                ThreadLocalDemo.threadLocal.remove();
//            }
        }
    }

    private void optionCompare() {
        try {
            SqlConnection sqlConnection = new SqlConnection(Thread.currentThread().getName());

            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " -get--" + sqlConnection.toString());

                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " T " + i + "-" + sqlConnection.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show() {
        SqlConnection sqlConnection = ThreadLocalDemo.threadLocal.get();
        if (null != sqlConnection) {
            System.out.println(Thread.currentThread().getName() + " - show before -" + sqlConnection.toString());
        } else {
            System.out.println(Thread.currentThread().getName() + " - show before - " + null);
        }

        ThreadLocalDemo.threadLocal.remove();

        sqlConnection = ThreadLocalDemo.threadLocal.get();
        if (null != sqlConnection) {
            System.out.println(Thread.currentThread().getName() + " - show after -" + sqlConnection.toString());
        } else {
            System.out.println(Thread.currentThread().getName() + " - show after - " + null);
        }
    }

    private class SqlConnection {
        public SqlConnection() {
        }

        public SqlConnection(String driverName) {
            this.driverName = driverName;
        }

        public SqlConnection(String driverName, String connAddress) {
            this.driverName = driverName;
            this.connAddress = connAddress;
        }

        private String driverName;
        private String connAddress = UUID.randomUUID().toString().substring(0, 5);

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getConnAddress() {
            return connAddress;
        }

        public void setConnAddress(String connAddress) {
            this.connAddress = connAddress;
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("  [");
            sb.append("\"driverName\":\"")
                    .append(driverName).append('\"');
            sb.append(",\"connAddress\":\"")
                    .append(connAddress).append('\"');
            sb.append(']');
            return sb.toString();
        }
    }
}
