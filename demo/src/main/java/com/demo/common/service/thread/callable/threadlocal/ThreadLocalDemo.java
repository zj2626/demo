package com.demo.common.service.thread.callable.threadlocal;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import lombok.Data;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

public class ThreadLocalDemo extends MyExcutor {
    // ThreadLocal的作用就是省的传参数 其保证每个线程中的某对象的独立--独享变量;(不像成员属性可能由于注入(共享)而导致线程安全问题), 可以理解为局部变量(形参)

    // ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景
    private static ThreadLocal<SqlConnection> threadLocal = new ThreadLocal<>();

    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        try {
            optionOne();

            System.out.println("SHOW------> " + Thread.currentThread().getName());
            show();
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
            // set的部分可以放到initialValue方法中执行
            if (null == threadLocal.get()) {
                SqlConnection sqlConnection = new SqlConnection(Thread.currentThread().getName());
                threadLocal.set(sqlConnection);
            }

            for (int i = 0; i < 5; i++) {
                // get得到null的时候会调用setInitialValue方法，从而调用initialValue方法，所以要set的值可以直接在initialValue中返回
                SqlConnection sqlConnection = threadLocal.get();
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " -get--" + sqlConnection);

                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " T " + i + "-" + sqlConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //            int a = (int) (1 + Math.random() * (10 - 1 + 1));
            //            if (a % 2 == 0) {
            threadLocal.remove();
            //            }
        }
    }

    private void show() {
        System.out.println(Thread.currentThread().getName() + " - show 1 -" + threadLocal.get());
        threadLocal.remove();
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 2 -" + threadLocal.get());
        SqlConnection sqlConnection = threadLocal.get();
        System.out.println(Thread.currentThread().getName() + " - show 3 -" + threadLocal.get());
        System.out.println(Thread.currentThread().getName() + " - show 4 -" + sqlConnection);
        threadLocal.remove();
        System.out.println(Thread.currentThread().getName() + " - show 5 -" + sqlConnection);
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 6 -" + threadLocal.get());
    }

    @Data
    private static class SqlConnection {

        public SqlConnection(String driverName) {
            this.driverName = driverName;
        }

        public SqlConnection(String driverName, String connAddress) {
            this.driverName = driverName;
            this.connAddress = connAddress;
        }

        private String driverName;
        private String connAddress = UUID.randomUUID().toString().substring(0, 5);

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
