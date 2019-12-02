package com.demo.common.service.thread.lock.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 初试
 */
public class ReentrantLockDemo implements Runnable {
    private ReentrantLock lock = new ReentrantLock();
    private int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo rd = new ReentrantLockDemo();

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd);
        System.out.println(rd.i);

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();

        System.out.println(rd.i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            lock.lock();

            try{
                i++;
            }finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }
}
