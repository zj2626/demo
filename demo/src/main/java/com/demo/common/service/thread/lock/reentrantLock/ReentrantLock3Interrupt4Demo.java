package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3Interrupt4Demo extends MyExcutor implements LockInterface {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 等同于lockInterruptibly()
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(3);
        Thread.sleep(3000);
        System.out.println("Interrupt");
        excutorPool.futureCancel();
        System.out.println("Interrupted");
        excutorPool.futureGet();
    }

    /**
     * tryLock
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        System.out.println(Thread.currentThread().getName() + " reentrantLock getLock");
        long n = 0;
        for (long i = 0; i < 5000000000L; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " reentrantLock interrupted " + n);
                break;
            }
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
