package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CountDownLatch 功能等于join()
 */
public class CountDownLatchDemo2 {
    private static final  int num = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(num);

        Job job = new Job(latch);

        ExecutorService service = Executors.newFixedThreadPool(num);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futureTasks.add(service.submit(job));
            Thread.sleep(100);
        }

        System.out.println("####1####");

        System.out.println("await");
        latch.await(1000, TimeUnit.MILLISECONDS);

        service.shutdown();

        System.out.println("####2####");
    }

    private static class Job implements Runnable {
        private CountDownLatch latch;

        public Job(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                for (int j = 0; j < 3; j++) {
                    System.out.println(Thread.currentThread().getName() + " -- " + j);
                    try {
                        Thread.sleep(150);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (latch != null) {
                    latch.countDown();
                    System.out.println("latch count: " + latch.getCount());
                }
            }
        }
    }
}
