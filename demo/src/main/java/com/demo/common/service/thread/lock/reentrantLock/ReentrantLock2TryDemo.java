package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock2TryDemo extends MyExcutor implements LockInterface {
    private static ReentrantLock lock = new ReentrantLock();
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
        System.out.println(count);
    }

    /**
     * 仅在调用时锁未被另一个线程保持的情况下，才获取该锁。
     * 1）如果该锁没有被另一个线程保持，并且立即返回 true 值，则将锁的保持计数设置为 1。
     * 即使已将此锁设置为使用公平排序策略，但是调用 tryLock() 仍将 立即获取锁（如果有可用的），
     * 而不管其他线程当前是否正在等待该锁。在某些情况下，此“闯入”行为可能很有用，即使它会打破公平性也如此。
     * 如果希望遵守此锁的公平设置，则使用 tryLock(0, TimeUnit.SECONDS) ，它几乎是等效的（也检测中断）。
     * 2）如果当前线程已经保持此锁，则将保持计数加 1，该方法将返回 true。
     * 3）如果锁被另一个线程保持，则此方法将立即返回 false 值。
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean getLock() throws Exception {
        return lock.tryLock(2000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void releaseLock() {
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }
}
