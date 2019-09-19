package com.demo.common.service.thread.reentrantLock;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁: 三种实现方式
 */
public class LockWaysCompare {

    /**
     * lock
     ********************************/

    @Test
    public void test() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LockWaysCompare.Job(reentrantLock));
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
                    System.out.println("Lock by:" + Thread.currentThread().getName() + " -> " + i);
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
        List<Thread> threads = new ArrayList<>();

        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new LockWaysCompare.Job2(reentrantLock));
            thread.setName("" + i);
            thread.start();

            threads.add(thread);

            Thread.sleep(10);
        }

        Thread.sleep(1500);

        Thread.sleep(2000);

        System.out.println("####");
    }

    private static class Job2 implements Runnable {
        private Lock lock;

        public Job2(Lock lock) {
            this.lock = lock;
        }

        // lockInterruptibly
        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    lock.lockInterruptibly();

                    System.out.println("working... " + Thread.currentThread().getName() + " -> " + i);

                    Thread.sleep(500);
                    System.out.println("\nLock by:" + Thread.currentThread().getName() + " -> " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                        System.err.println(e.getMessage() + Thread.currentThread().getName());
                    }
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
            Thread thread = new Thread(new LockWaysCompare.Job3(reentrantLock));
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
//                    boolean tryLock = lock.tryLock(0, TimeUnit.MILLISECONDS);
//                    boolean tryLock = lock.tryLock();
                    System.out.println(i + "**" + Thread.currentThread().getName() + "**" + tryLock);

                    Thread.sleep(10);

                    if (tryLock) {
                        System.out.println("Lock by:" + Thread.currentThread().getName() + " -> " + i);
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
