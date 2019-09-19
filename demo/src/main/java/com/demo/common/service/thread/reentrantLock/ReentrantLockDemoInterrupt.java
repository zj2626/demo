package com.demo.common.service.thread.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 中断 中断机制
 */
public class ReentrantLockDemoInterrupt implements Runnable {
    private static ReentrantLock reentrantLock_1 = new ReentrantLock();
    private static ReentrantLock reentrantLock_2 = new ReentrantLock();
    private int lock;

    public ReentrantLockDemoInterrupt(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemoInterrupt rd = new ReentrantLockDemoInterrupt(1);
        ReentrantLockDemoInterrupt rd2 = new ReentrantLockDemoInterrupt(2);

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd2);

        thread.start();
        thread2.start();

        Thread.sleep(4000);

        //ReenTrantLock提供了一种能够中断等待锁的线程的机制，通过lock.lockInterruptibly()来实现这个机制
        thread2.interrupt();

        thread.join();
        thread2.join();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                if (lock == 1) {
                    System.out.println("rd 进入");
                    reentrantLock_1.lock();
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                    reentrantLock_2.lockInterruptibly();
                    System.out.println("\ndo some 1\n");

                } else {
                    System.out.println("rd2 进入");
                    reentrantLock_2.lockInterruptibly();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    reentrantLock_1.lockInterruptibly();
                    System.out.println("\ndo some 2\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName());
                System.out.println("release_____________");

                if (reentrantLock_1.isHeldByCurrentThread()) {
                    reentrantLock_1.unlock();
                }
                if (reentrantLock_2.isHeldByCurrentThread()) {
                    reentrantLock_2.unlock();
                }

            }
        }
    }
}
