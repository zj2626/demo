package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 功能等于join()
 */
public class CountDownLatchDemo {
    private static final  int num = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(num);

        ExecutorService service = Executors.newFixedThreadPool(num);
        List<Job> jobList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Job rd = new Job(latch);
            jobList.add(rd);
        }

        for (Job job : jobList) {
            service.submit(job);
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
