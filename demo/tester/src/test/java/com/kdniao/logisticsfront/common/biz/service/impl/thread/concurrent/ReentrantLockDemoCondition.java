package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 and Condition
 */
public class ReentrantLockDemoCondition implements Runnable {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition condition = reentrantLock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemoCondition rd = new ReentrantLockDemoCondition();

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd);
        Thread thread3 = new Thread(rd);
        Thread thread4 = new Thread(rd);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        Thread.sleep(100);
        reentrantLock.lock();
        System.out.println("\nF-----------");
        condition.signal();
        reentrantLock.unlock();

        Thread.sleep(100);
        reentrantLock.lock();
        System.out.println("\nF-----------");
        condition.signal();
        reentrantLock.unlock();

        Thread.sleep(100);
        reentrantLock.lock();
        System.out.println("\nF-----------");
        condition.signal();
        reentrantLock.unlock();

        Thread.sleep(100);
        reentrantLock.lock();
        System.out.println("\nF-----------");
        condition.signal();
        reentrantLock.unlock();

        // 可以使用signalAll唤醒所有的线程[相当于notifyAll()]
//        Thread.sleep(100);
//        reentrantLock.lock();
//        System.out.println("\nF-----------");
//        condition.signalAll();
//        reentrantLock.unlock();

        thread.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }

    @Override
    public void run() {
        try {
            reentrantLock.lock();
            for (int i = 0; i < 3; i++) {
                Thread.sleep(200);


                System.out.println(Thread.currentThread().getName() + " A " + i);
                if (i == 1) {
                    // 当线程使用condition.await()的时候，要求线程持有相关的锁，当线程调用condition.await()之后，这个线程会释放持有的锁，并进入等待状态[相当于wait()[相当于notify()]]; 需要执行signal()进行唤醒
                    condition.await();
//                    wait();
                }
                System.out.println(Thread.currentThread().getName() + " B " + i);

                System.out.println(Thread.currentThread().getName() + " do some " + i);
            }
        } catch (Exception ignored) {
        } finally {
            System.out.println(Thread.currentThread().getName() + " release " + "\n");
            reentrantLock.unlock();
        }
    }
}
