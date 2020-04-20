package com.demo.common.service.thread.callable.threadlocal;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import lombok.Data;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

public class ThreadLocalDemo2 extends MyExcutor {
    // ThreadLocal的作用就是省的传参数 其保证每个线程中的某对象的独立--独享变量;(不像成员属性可能由于注入(共享)而导致线程安全问题), 可以理解为局部变量(形参)

    // ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景
    private static ThreadLocal<SqlConnection> threadLocal = ThreadLocal.withInitial(() -> {
        System.out.println('\n' + Thread.currentThread().getName() + " >>>> initialValue");
        SqlConnection sqlConnection = new SqlConnection(Thread.currentThread().getName());
        return sqlConnection;
    });

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

    private void optionOne() {
        try {
            for (int i = 0; i < 5; i++) {
                // get得到null的时候会调用setInitialValue方法，从而调用initialValue方法，所以要set的值可以直接在initialValue中返回
                SqlConnection sqlConnection = ThreadLocalDemo2.threadLocal.get();
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
            ThreadLocalDemo2.threadLocal.remove();
            //            }
        }
    }

    private void show() {
        System.out.println(Thread.currentThread().getName() + " - show 1 -" + ThreadLocalDemo2.threadLocal.get());
        ThreadLocalDemo2.threadLocal.remove();
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 2 -" + ThreadLocalDemo2.threadLocal.get());
        SqlConnection sqlConnection = ThreadLocalDemo2.threadLocal.get();
        System.out.println(Thread.currentThread().getName() + " - show 3 -" + ThreadLocalDemo2.threadLocal.get());
        System.out.println(Thread.currentThread().getName() + " - show 4 -" + sqlConnection);
        ThreadLocalDemo2.threadLocal.remove();
        System.out.println(Thread.currentThread().getName() + " - show 5 -" + sqlConnection);
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 6 -" + ThreadLocalDemo2.threadLocal.get());
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
