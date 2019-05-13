package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 中断 中断机制
 */
public class ReentrantLockDemoInterrupt2 {

    @Test
    public void test2() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new ReentrantLockDemoInterrupt2.Job2(reentrantLock));
            thread.setName("" + i);
            thread.start();

            threads.add(thread);

            Thread.sleep(10);
        }

        Thread.sleep(1800);

        System.out.println("\n************\ninterrupt\n************\n");
        threads.get(0).interrupt();
        threads.get(1).interrupt();
        threads.get(2).interrupt();
        threads.get(3).interrupt();
        threads.get(4).interrupt();

        Thread.sleep(3000);

        System.out.println("####");
    }

    /**
     * ReentrantLock.lockInterruptibly允许在等待时由其它线程调用等待线程的Thread.interrupt方法来中断等待线程的等待而直接返回，这时不用获取锁，而会抛出一个InterruptedException。
     * ReentrantLock.lock方法不允许Thread.interrupt中断,即使检测到Thread.isInterrupted,一样会继续尝试获取锁，失败则继续休眠。只是在最后获取锁成功后再把当前线程置为interrupted状态,然后再中断线程。
     */
    private static class Job2 implements Runnable {
        private Lock lock;

        public Job2(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            int ift = 1;

            // lockInterruptibly
            if (ift == 1) {
                for (int i = 0; i < 2; i++) {
                    try {
                        lock.lockInterruptibly();

                        System.out.println("working... " + Thread.currentThread().getName() + "->" + i);

                        for (int j = 0; j < 20; j++) {
                            if (j == 0 || j == 1) {
                                System.out.print("########\t");
                            }
                            Thread.sleep(25);
                            System.out.print(Thread.currentThread().getName() + "->" + i + "-" + j + " ");
                        }
                        System.out.println("\nLock by:" + Thread.currentThread().getName() + "->" + i);
                    } catch (Exception e) {
                        System.err.println("exception by:" + Thread.currentThread().getName() + "->" + i);
//                    e.printStackTrace();
                    } finally {
                        System.out.println("unlock by:" + Thread.currentThread().getName() + "->" + i);
                        try {
                            lock.unlock();
                        } catch (Exception e) {
                            System.err.println(e.getMessage() + Thread.currentThread().getName());
                        }
                    }
                }
            }

            // tryLock (效果和lockInterruptibly一样, 不同的是有个超时时间)
            if (ift == 2) {
                for (int i = 0; i < 2; i++) {
                    try {
                        lock.tryLock(2000, TimeUnit.MILLISECONDS);

                        System.out.println("working... " + Thread.currentThread().getName() + "->" + i);

                        for (int j = 0; j < 20; j++) {
                            if (j == 0 || j == 1) {
                                System.out.print("########\t"); // 当lock.lock()时执行了中断, 并不会中断获取说会在
                            }
                            Thread.sleep(25);
                            System.out.print(Thread.currentThread().getName() + "->" + i + "-" + j + " ");
                        }
                        System.out.println("\nLock by:" + Thread.currentThread().getName() + "->" + i);
                    } catch (Exception e) {
                        System.err.println("exception by:" + Thread.currentThread().getName() + "->" + i);
//                        e.printStackTrace();
                    } finally {
                        System.out.println("unlock by:" + Thread.currentThread().getName() + "->" + i);
                        try {
                            lock.unlock();
                        } catch (Exception e) {
                            System.err.println(e.getMessage() + Thread.currentThread().getName());
                        }
                    }
                }
            }

            // lock
//            if (ift == 3) {
//                for (int i = 0; i < 2; i++) {
//                    try {
//                        lock.lock();
//
//                        System.out.println("working... " + Thread.currentThread().getName() + "->" + i);
//
//                        for (int j = 0; j < 20; j++) {
//                            if (j == 0 || j == 1) {
//                                System.out.print("########\t"); // 当lock.lock()时执行了中断, 并不会中断获取说会在
//                            }
//                            Thread.sleep(25);
//                            System.out.print(Thread.currentThread().getName() + "->" + i + "-" + j + " ");
//                        }
//                        System.out.println("\nLock by:" + Thread.currentThread().getName() + "->" + i);
//                    } catch (Exception e) {
//                        System.err.println("exception by:" + Thread.currentThread().getName() + "->" + i);
////                    e.printStackTrace();
//                    } finally {
//                        System.out.println("unlock by:" + Thread.currentThread().getName() + "->" + i);
//                        try {
//                            lock.unlock();
//                        } catch (Exception e) {
//                            System.err.println(e.getMessage() + Thread.currentThread().getName());
//                        }
//                    }
//                }
//            }

        }
    }
}
