package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    @Test
    public void testFair() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(new Job(reentrantLock));
            thread.setName("" + i);
            thread.start();
//            Thread.sleep(50);
        }

        Thread.sleep(5000);
    }

    @Test
    public void testNotFair() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(new Job(reentrantLock));
            thread.setName("" + i);
            thread.start();
//            Thread.sleep(50);
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
            for (int i = 0; i < 3; i++) {
                try {
//                    Thread.sleep(50);

                    lock.lock();

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
