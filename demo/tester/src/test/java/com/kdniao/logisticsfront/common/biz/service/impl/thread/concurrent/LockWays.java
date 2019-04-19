package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockWays {

    /**
     * lock
     ********************************/

    @Test
    public void test() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LockWays.Job(reentrantLock));
            thread.setName("" + i);
            thread.start();

            Thread.sleep(50);
        }

        Thread.sleep(12000);

        System.out.println("####");
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

                    System.out.println("working... ");

                    Thread.sleep(100);
                    System.out.println("Lock by:" + i + " -> " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * lockInterruptibly
     *************************/

    @Test
    public void test2() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LockWays.Job2(reentrantLock));
            thread.setName("" + i);
            thread.start();

            Thread.sleep(50);
        }

        Thread.sleep(12000);

        System.out.println("####");
    }

    private static class Job2 implements Runnable {
        private Lock lock;

        public Job2(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    lock.lockInterruptibly();

                    System.out.println("working... ");

                    Thread.sleep(500);
                    System.out.println("Lock by:" + i + " -> " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * tryLock
     *************************/

    @Test
    public void test3() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LockWays.Job3(reentrantLock));
            thread.setName("" + i);
            thread.start();

            Thread.sleep(50);
        }

        Thread.sleep(12000);

        System.out.println("####");
    }

    private static class Job3 implements Runnable {
        private Lock lock;

        public Job3(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    boolean tryLock = lock.tryLock(500, TimeUnit.MILLISECONDS);
//                    boolean tryLock = lock.tryLock();
                    System.out.println(i + "**" + Thread.currentThread().getName() + "**" + tryLock);

                    Thread.sleep(10);

                    if (tryLock) {
                        System.out.println("Lock by:" + i + " -> " + Thread.currentThread().getName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // nothing
                }
            }
        }
    }
}
