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
    public String doExcute(Map<String, String> parameter) throws Exception {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(1);
            count++;
        }
        return null;
    }
}
