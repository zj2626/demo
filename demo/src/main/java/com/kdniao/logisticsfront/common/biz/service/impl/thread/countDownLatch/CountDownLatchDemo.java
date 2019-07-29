package com.kdniao.logisticsfront.common.biz.service.impl.thread.countDownLatch;

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
            rd.setSize(i+1);
            jobList.add(rd);
        }

        for (Job job : jobList) {
            service.submit(job);
            Thread.sleep(10);
        }

        System.out.println("####1####");

        latch.await(10000, TimeUnit.MILLISECONDS); //会阻塞最大10s直到其count为0或者超时
        System.out.println("awaited");

        service.submit(new Job(latch)); // 不会再countDown了
        service.submit(new Job(latch));

        System.out.println("####2####");

        Thread.sleep(10000);

        service.shutdown();

    }

    private static class Job implements Runnable {
        private int size = 2;
        private CountDownLatch latch;

        public Job(CountDownLatch latch) {
            this.latch = latch;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public void run() {
            try {
                for (int j = 0; j < size; j++) {
                    System.out.println(j + " -- " + size + "-" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
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
