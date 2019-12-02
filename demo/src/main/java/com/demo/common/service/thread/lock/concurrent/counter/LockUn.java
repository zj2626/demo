package com.demo.common.service.thread.lock.concurrent.counter;

/**
 * 不可重入
 */
public class LockUn {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "----" + isLocked);
        while (isLocked) {
            System.out.println(Thread.currentThread().getName() + " wait ");
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        System.out.println(Thread.currentThread().getName() + " notify");
        notify();
    }
}