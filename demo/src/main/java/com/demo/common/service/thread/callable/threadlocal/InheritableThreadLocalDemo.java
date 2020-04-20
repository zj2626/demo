package com.demo.common.service.thread.callable.threadlocal;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import lombok.Data;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

/**
 * @name: InheritableThreadLocalDemo
 * @description: InheritableThreadLocal
 * @author: zhangj
 * @create: 2020-04-20 19:56
 **/
public class InheritableThreadLocalDemo extends MyExcutor {

    private static InheritableThreadLocal<InheritableThreadLocalDemo.SqlConnection> threadLocal = new InheritableThreadLocal();

    @Test
    public void test() throws InterruptedException {
        InheritableThreadLocalDemo.SqlConnection sqlConnection =
                new InheritableThreadLocalDemo.SqlConnection(Thread.currentThread().getName());
        InheritableThreadLocalDemo.threadLocal.set(sqlConnection);
        System.out.println(Thread.currentThread().getName() + " -get--" + InheritableThreadLocalDemo.threadLocal.get());

        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);

        for (int i = 0; i < 15; i++) {
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + " -get--" + InheritableThreadLocalDemo.threadLocal.get());
            if(i >= 5){
                InheritableThreadLocalDemo.threadLocal.remove();
            }
        }
        excutorPool.futureGet();
        System.out.println(Thread.currentThread().getName() + " -get--" + InheritableThreadLocalDemo.threadLocal.get());

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
            for (int i = 0; i < 5; i++) {
                // get得到null的时候会调用setInitialValue方法，从而调用initialValue方法，所以要set的值可以直接在initialValue中返回
                InheritableThreadLocalDemo.SqlConnection sqlConnection = InheritableThreadLocalDemo.threadLocal.get();
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " -get--" + sqlConnection);

                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " T " + i + "-" + sqlConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InheritableThreadLocalDemo.threadLocal.remove();
        }
    }

    private void show() {
        System.out.println(Thread.currentThread().getName() + " - show 1 -" + InheritableThreadLocalDemo.threadLocal.get());
        InheritableThreadLocalDemo.threadLocal.remove();
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 2 -" + InheritableThreadLocalDemo.threadLocal.get());
        InheritableThreadLocalDemo.SqlConnection sqlConnection = InheritableThreadLocalDemo.threadLocal.get();
        System.out.println(Thread.currentThread().getName() + " - show 3 -" + InheritableThreadLocalDemo.threadLocal.get());
        System.out.println(Thread.currentThread().getName() + " - show 4 -" + sqlConnection);
        InheritableThreadLocalDemo.threadLocal.remove();
        System.out.println(Thread.currentThread().getName() + " - show 5 -" + sqlConnection);
        // initialValue
        System.out.println(Thread.currentThread().getName() + " - show 6 -" + InheritableThreadLocalDemo.threadLocal.get());
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
