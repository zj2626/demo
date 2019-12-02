package com.demo.common.service.thread.lock.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁 and Condition
 */
public class ReentrantLockDemoCondition2 {
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition conditionA = reentrantLock.newCondition();
    private static Condition conditionB = reentrantLock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Run1Test rd1 = new Run1Test(true);
        Run2Test rd2 = new Run2Test(true);
        Run2Test rd3 = new Run2Test(true);

        Thread thread = new Thread(rd1);
        Thread thread2 = new Thread(rd2);
        Thread thread3 = new Thread(rd3);

        thread.start();
        thread2.start();
        thread3.start();

        // 相比较synchronize的wait()和notify()/notifAll()的机制而言 Condition具有更高的灵活性。Conditon可以实现多路通知和选择性通知

        // 可以使用signalAll唤醒所有的线程[相当于notifyAll()]
        if (rd2.ifBlock) {
            Thread.sleep(3000);
            reentrantLock.lock();
            System.out.println("\nF--唤醒 conditionB阻塞的线程---------");
            conditionB.signalAll();
            reentrantLock.unlock();
        }

        if (rd1.ifBlock) {
            Thread.sleep(3000);
            reentrantLock.lock();
            System.out.println("\nF--唤醒 conditionA阻塞的线程---------");
            conditionA.signalAll();
            reentrantLock.unlock();
        }

        thread.join();
        thread2.join();
        thread3.join();
    }

    private static class Run1Test implements Runnable {
        private boolean ifBlock;

        Run1Test(boolean ifBlock) {
            this.ifBlock = ifBlock;
        }

        @Override
        public void run() {
            try {
                reentrantLock.lock();

                for (int i = 0; i < 3; i++) {
                    Thread.sleep(100);

                    System.out.println(Thread.currentThread().getName() + " A " + i);
                    Thread.sleep(200);
                    if (ifBlock && i == 2) {
                        conditionA.await();
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

    private static class Run2Test implements Runnable {
        private boolean ifBlock;

        Run2Test(boolean ifBlock) {
            this.ifBlock = ifBlock;
        }

        @Override
        public void run() {
            try {
                reentrantLock.lock();

                for (int i = 0; i < 3; i++) {
                    Thread.sleep(100);

                    System.out.println(Thread.currentThread().getName() + " A " + i);
                    Thread.sleep(200);
                    if (ifBlock && i == 2) {
                        conditionB.await();
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
}
