package com.demo.common.service.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest implements Runnable {
    private String name = "f";
    private Integer num = 0;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest syncTest = new SynchronizedTest();

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            syncTest.setName(new String("abc" + i));
            service.submit(syncTest);
            Thread.sleep(1);
        }
        Thread.sleep(10000);
        service.shutdown();

        System.out.println(syncTest.num);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000000; i++) {
//            synchronized (getName()) {
//                num++;
//            }

            synchronized (this) {
                num++;
            }

//            synchronized (SynchronizedTest.class) {
//                num++;
//            }
        }
    }
}
