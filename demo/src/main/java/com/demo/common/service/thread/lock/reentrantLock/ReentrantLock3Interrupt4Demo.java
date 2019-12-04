package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3Interrupt4Demo extends MyExcutor implements LockInterface {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(3);
        Thread.sleep(2000);
        System.out.println("Interrupt");
        threadExcutor.futureCancel();
        threadExcutor.futureGet();
    }

    /**
     * tryLock
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        System.out.println(Thread.currentThread().getName() + " reentrantLock getLock");
        int n = 0;
        for (long i = 0; i < 5000000000L; i++) {
            n++;
        }
        System.out.println(Thread.currentThread().getName() + " reentrantLock inLock");
        return null;
    }

    @Override
    public boolean getLock() throws Exception {
        reentrantLock.tryLock(5, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public void releaseLock() {
        if (reentrantLock.isLocked() && reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock();
        }
    }
}
