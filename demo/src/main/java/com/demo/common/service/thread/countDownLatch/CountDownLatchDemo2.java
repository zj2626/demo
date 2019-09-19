package com.demo.common.service.thread.countDownLatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * CountDownLatch 功能等于join()
 */
public class CountDownLatchDemo2 {
    private static final int num = 5;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(num);

        Job job = new Job(latch);

        ExecutorService service = Executors.newFixedThreadPool(num);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futureTasks.add(service.submit(job));
            Thread.sleep(10);
        }

        System.out.println("####1####");

        System.out.println("await");
        latch.await(10000, TimeUnit.MILLISECONDS); //会阻塞最大10s直到其count为0或者超时
        System.out.println("awaited");

        service.submit(new Job(latch));

        System.out.println("####2####");

        service.shutdown();
    }

    private static class Job implements Runnable {
        private CountDownLatch latch;

        public Job(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            int number = Integer.valueOf(Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1));

            try {
                for (int j = 0; j < number; j++) {
                    System.out.println(Thread.currentThread().getName() + " -- " + j);
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
