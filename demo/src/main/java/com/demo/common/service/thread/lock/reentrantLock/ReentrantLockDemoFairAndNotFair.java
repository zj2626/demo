package com.demo.common.service.thread.lock.reentrantLock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁: 公平锁 非公平锁
 */
public class ReentrantLockDemoFairAndNotFair {

    @Test
    public void testFair() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(new Job(reentrantLock));
            thread.setName("" + i);
            thread.start();

            Thread.sleep(10);
        }

        Thread.sleep(5000);
    }

    @Test
    public void testNotFair() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(new Job(reentrantLock));
            thread.setName("" + i);
            thread.start();

            Thread.sleep(10);
        }

        Thread.sleep(10000);
    }

    private static class Job implements Runnable {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                try {

                    lock.lock();

                    Thread.sleep(50);

                    System.out.println("Lock by:" + i + " -> " + Thread.currentThread().getName());

//                    if ("1".equals(Thread.currentThread().getName()) && i == 1) {
//                        System.out.println("_____________________");
//                        Thread.sleep(3000);
//                        System.out.println(">>>>>>>>>>");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
