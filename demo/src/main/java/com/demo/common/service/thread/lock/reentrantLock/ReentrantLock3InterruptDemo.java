package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3InterruptDemo extends MyExcutor implements LockInterface {
    private static ReentrantLock lock = new ReentrantLock(); // true:公平或 false(默认):非公平
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
        calculate(50L * 1 * 20);
        System.out.println(count);
    }

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

    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }
}
