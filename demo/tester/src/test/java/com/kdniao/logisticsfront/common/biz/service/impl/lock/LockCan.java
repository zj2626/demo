package com.kdniao.logisticsfront.common.biz.service.impl.lock;

public class LockCan {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;

    public synchronized void lock()
            throws InterruptedException {
        Thread thread = Thread.currentThread();
        while (isLocked && lockedBy != thread) {
            System.out.println(Thread.currentThread().getName() + " wait ");
            wait();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == this.lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                System.out.println(Thread.currentThread().getName() + " notify");
                notifyAll();
            }
        }
    }
}