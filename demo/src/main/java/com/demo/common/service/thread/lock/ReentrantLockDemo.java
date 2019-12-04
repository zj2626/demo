package com.demo.common.service.thread.lock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo extends MyExcutor implements LockInterface {
    private static ReentrantLock lock = new ReentrantLock(); // true:公平或 false(默认):非公平
    private static int count = 0;

    /**
     * ReentrantLock 实现接口->Lock接口
     * |             组合实现->Sync抽象类 包含其两个之类:NonfairSync,FairSync
     * |             Sync继承自AbstractQueuedSynchronizer, 即AQS (AQS内部有一个FIFO队列)
     */
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
        calculate(50L * 1 * 20);
        System.out.println(count);
    }

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

    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }
}
