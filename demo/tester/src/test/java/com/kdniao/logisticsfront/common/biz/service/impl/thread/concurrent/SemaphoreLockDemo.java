package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

/**
 * 信号量：Semaphore  :信号量允许多个线程同时访问同一个资源
 */
public class SemaphoreLockDemo implements Runnable {
    private Integer sum = 0;
    private boolean ifBlock;

    public SemaphoreLockDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    // permits表示一次允许多少个线程， fair表示是否公平锁
    private static Semaphore semaphore = new Semaphore(1, true);

    public static void main(String[] args) throws InterruptedException {
        SemaphoreLockDemo rd = new SemaphoreLockDemo(true);

        // way one
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            futureTasks.add(service.submit(rd));
            Thread.sleep(1);
        }
        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();

        // way two
//        Thread thread = new Thread(rd);
//        Thread thread2 = new Thread(rd);
//        Thread thread3 = new Thread(rd);
//        Thread thread4 = new Thread(rd);
//        thread.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
////        if (rd.ifBlock) {
////            Thread.sleep(1500);
////            System.out.println("interrupt");
////            thread3.interrupt();
////        }
//        thread.join();
//        thread2.join();
//        thread3.join();
//        thread4.join();

        System.out.println(rd.sum);
    }

    @Override
    public void run() {

        // option one
        try {
            semaphore.acquire();
//            semaphore.acquireUninterruptibly();
//            boolean tryAcquire = semaphore.tryAcquire(1, 1000, TimeUnit.SECONDS);
//            System.out.println(tryAcquire);

            // System.out.println(semaphore.hashCode()); 除了semaphore,都是同时只允许一个线程获得锁

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " A " + i);
                Thread.sleep(300);
                sum++;
                System.out.println(Thread.currentThread().getName() + " B " + i);
                Thread.sleep(600);
                if (ifBlock && i == 2) {

                }
                System.out.println(Thread.currentThread().getName() + " C " + i + "-" + sum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release " + "\n");

            semaphore.release();
        }

        // option two
//        try {
//            semaphore.acquire();
//            System.out.println("2---" + Thread.currentThread().getName() + " begin");
//            for (int j = 0; j < 100000; j++) {
//                sum++;
//            }
//            System.out.println("2---" + Thread.currentThread().getName() + " done " + sum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            semaphore.release();
//        }

        // option three
//        System.out.println("3---" + Thread.currentThread().getName() + " begin");
//        for (int j = 0; j < 100000; j++) {
//            try {
//                semaphore.acquire();
//                sum++;
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                semaphore.release();
//            }
//        }
//        System.out.println("3---" + Thread.currentThread().getName() + " done " + sum);
    }
}
