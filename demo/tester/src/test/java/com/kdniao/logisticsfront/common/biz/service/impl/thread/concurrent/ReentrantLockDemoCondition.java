package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 and Condition
 */
public class ReentrantLockDemoCondition implements Runnable {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition conditionA = reentrantLock.newCondition();
    private static Condition conditionB = reentrantLock.newCondition();
    private boolean ifBlock;

    public ReentrantLockDemoCondition(boolean ifBlock) {
        this.ifBlock = ifBlock;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemoCondition rd = new ReentrantLockDemoCondition(true);

        Thread thread = new Thread(rd);
        Thread thread2 = new Thread(rd);
        Thread thread3 = new Thread(rd);
        Thread thread4 = new Thread(rd);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();

        if (rd.ifBlock) {
            Thread.sleep(5000);
            System.out.println("\n开始激活block的线程...");
            reentrantLock.lock();
            System.out.println("\nF-----------");
            conditionA.signal();
            reentrantLock.unlock();

            Thread.sleep(1000);
            reentrantLock.lock();
            System.out.println("\nF-----------");
            conditionA.signal();
            reentrantLock.unlock();

            Thread.sleep(1000);
            reentrantLock.lock();
            System.out.println("\nF-----------");
            conditionA.signal();
            reentrantLock.unlock();

            Thread.sleep(1000);
            reentrantLock.lock();
            System.out.println("\nF-----------");
            conditionA.signal();
            reentrantLock.unlock();

            // 可以使用signalAll唤醒所有的线程[相当于notifyAll()]
//            Thread.sleep(3000);
//            reentrantLock.lock();
//            System.out.println("\nF--22222222222222222222222222---------");
//            conditionB.signalAll();
//            reentrantLock.unlock();

        }

        thread.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }

    @Override
    public void run() {
        try {
            reentrantLock.lock();

            // System.out.println(reentrantLock.hashCode()); 除了semaphore,都是同时只允许一个线程获得锁

            for (int i = 0; i < 4; i++) {
                Thread.sleep(100);

                System.out.println(Thread.currentThread().getName() + " A " + i);
                Thread.sleep(200);
                if (ifBlock && i == 2) {
                    // 当线程使用condition.await()的时候，要求线程持有相关的锁，当线程调用condition.await()之后，这个线程会释放持有的锁，并进入等待状态[相当于wait()[相当于notify()]]; 需要执行signal()进行唤醒
                    conditionA.await();
                }
//                if (ifBlock && i == 3) {
//                    conditionB.await();
//                }

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
