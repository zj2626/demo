package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo2 implements Runnable {
    private static ReentrantLock reentrantLock_1 = new ReentrantLock();
    private static ReentrantLock reentrantLock_2 = new ReentrantLock();
    private int lock;

    public ReentrantLockDemo2(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo2 rd = new ReentrantLockDemo2(1);
        ReentrantLockDemo2 rd2 = new ReentrantLockDemo2(2);

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd2);

        thread.start();
        thread2.start();

        Thread.sleep(2000);

        thread2.interrupt();

        thread.join();
        thread2.join();
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                // rd 进入
                reentrantLock_1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                reentrantLock_2.lockInterruptibly();
                System.out.println("\ndo some 1\n");

            } else {
                // rd2 进入
                reentrantLock_2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                reentrantLock_1.lockInterruptibly();
                System.out.println("\ndo some 2\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("release");
            if(reentrantLock_1.isHeldByCurrentThread()){
                reentrantLock_1.unlock();
            }
            if(reentrantLock_2.isHeldByCurrentThread()){
                reentrantLock_2.unlock();
            }

            System.out.println(Thread.currentThread().getName());
        }
    }
}
