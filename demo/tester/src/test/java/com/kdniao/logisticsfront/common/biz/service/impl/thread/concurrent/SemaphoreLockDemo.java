package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 信号量：Semaphore  :信号量允许多个线程同时访问同一个资源
 */
public class SemaphoreLockDemo implements Runnable {
    private boolean ifBlock;

    public SemaphoreLockDemo(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    // permits表示一次允许多少个线程， fair表示是否公平锁
    private static Semaphore semaphore = new Semaphore(2, false);

    public static void main(String[] args) throws InterruptedException {
        SemaphoreLockDemo rd = new SemaphoreLockDemo(false);

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd);
        Thread thread3 = new Thread(rd);
        Thread thread4 = new Thread(rd);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        if (rd.ifBlock) {

        }

        thread.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            // System.out.println(semaphore.hashCode()); 除了semaphore,都是同时只允许一个线程获得锁

            for (int i = 0; i < 3; i++) {
                Thread.sleep(100);

                System.out.println(Thread.currentThread().getName() + " A " + i);
                Thread.sleep(200);
                if (ifBlock && i == 2) {

                }
                System.out.println(Thread.currentThread().getName() + " B " + i);

                System.out.println(Thread.currentThread().getName() + " do some " + i);
            }
        } catch (Exception ignored) {
        } finally {
            System.out.println(Thread.currentThread().getName() + " release " + "\n");

            semaphore.release();
        }
    }
}
