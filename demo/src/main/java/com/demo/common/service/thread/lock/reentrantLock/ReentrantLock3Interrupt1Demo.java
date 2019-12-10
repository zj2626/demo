package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3Interrupt1Demo extends MyExcutor implements LockInterface {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
        System.out.println(count);
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }

    /**
     * 1）如果当前线程未被中断，则获取锁。
     * 2）如果该锁没有被另一个线程保持，则获取该锁并立即返回，将锁的保持计数设置为 1。
     * 3）如果当前线程已经保持此锁，则将保持计数加 1，并且该方法立即返回。
     * 4）如果锁被另一个线程保持，则出于线程调度目的，禁用当前线程，并且在发生以下两种情况之一以前，该线程将一直处于休眠状态：
     * *      1）锁由当前线程获得；或者
     * *      2）其他某个线程中断当前线程。
     * 5）如果当前线程获得该锁，则将锁保持计数设置为 1。
     * 如果当前线程：
     * *        1）在进入此方法时已经设置了该线程的中断状态(eq:sleep,wait,join)；或者
     * *        2）在等待获取锁的同时被中断。
     * 则抛出 InterruptedException，并且清除当前线程的已中断状态。
     * 6）在此实现中，因为此方法是一个显式中断点，所以要优先考虑响应中断，而不是响应锁的普通获取或重入获取。
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean getLock() throws Exception {
        lock.lockInterruptibly();
        return true;
    }

    @Override
    public void releaseLock() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
