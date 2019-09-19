package com.demo.common.service.thread.wait.sleep.join;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WaitVsSleepDemo {
    /*wait 和 sleep区别
    *
    *
    * wait: 当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态.
    * */
    @Test
    public void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Future future = executorService.submit(() -> {
            System.out.println("AAAA");

            Thread.sleep(2000);

            return "fuck";
        });

        System.out.println("SSSSSSSSSSS");

        try {
            Object result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Fun {
        int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    public void test3() throws InterruptedException {
        Fun fun = new Fun();

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                System.out.println(fun.getAge());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            synchronized (fun) {
                fun.setAge(fun.getAge() + 10);
                try {
//                    Thread.sleep(2000);
//                    Thread.yield();
                    fun.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fun.setAge(fun.getAge() + 10);
            }
        });
        thread2.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("AAA" + fun.getAge());
            synchronized (fun) {
                System.out.println("BBB" + fun.getAge());
                fun.setAge(fun.getAge() + 100);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                fun.notify();
            }

        }).start();

//        thread2.join();
        thread.join();
        System.out.println("############################");
    }
}
