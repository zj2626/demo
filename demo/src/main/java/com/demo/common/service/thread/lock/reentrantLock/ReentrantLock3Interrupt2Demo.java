package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3Interrupt2Demo extends MyExcutor implements LockInterface {
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

    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            System.out.println(Thread.currentThread().getName() + " reentrantLock a");
            Thread.sleep(1500);
            System.out.println(Thread.currentThread().getName() + " reentrantLock b");
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " ERROR");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getLock() throws Exception {
        reentrantLock.lockInterruptibly();
        return true;
    }

    @Override
    public void releaseLock() {
        if (reentrantLock.isLocked() && reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock();
        }
    }
}
