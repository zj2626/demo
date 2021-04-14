package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock1Demo extends MyExcutor implements LockInterface {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    /**
     * ReentrantLock 实现接口->Lock接口
     * |             组合实现->Sync抽象类 包含其两个之类:NonfairSync,FairSync
     * |             Sync继承自AbstractQueuedSynchronizer, 即AQS (AQS内部有一个FIFO队列)
     */
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
        System.out.println(count);
    }

    @Override
    public Object doExcute() throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }

    /**
     * 获取锁。
     * 如果该锁没有被另一个线程保持，则获取该锁并立即返回，将锁的保持计数设置为 1。
     * 如果当前线程已经保持该锁，则将保持计数加 1，并且该方法立即返回。
     * 如果该锁被另一个线程保持，则出于线程调度的目的，禁用当前线程，并且在获得锁之前，该线程将一直处于休眠状态，此时锁保持计数被设置为 1。
     *
     * @return
     */
    @Override
    public boolean getLock() {
        lock.lock();
        return true;
    }

    @Override
    public void releaseLock() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
