package com.kdniao.logisticsfront.common.biz.service.impl.thread.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo{
    private static ReentrantLock lock = new ReentrantLock(); // true:公平或 false(默认):非公平
    private static int count = 0;

    /**
     * ReentrantLock 实现接口->Lock接口
     * |             组合实现->Sync抽象类 包含其两个之类:NonfairSync,FairSync
     * |             Sync继承自AbstractQueuedSynchronizer, 即AQS (AQS内部有一个FIFO队列)
     */
    @Test
    public void test() throws InterruptedException {
        new Thread(ReentrantLockDemo::increment).start();
        new Thread(ReentrantLockDemo::increment).start();

        Thread.sleep(200);

        System.out.println(count);
    }



    public static void increment(){
        lock.lock();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;

        lock.unlock();
    }
}
