package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock4InterruptedDemo extends MyExcutor implements LockInterface {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        Thread.sleep(200);
        System.out.println("Interrupt");
        excutorPool.futureCancel();
        System.out.println("Interrupted");
        excutorPool.futureGet();
    }

    /**
     * lockInterruptibly
     *
     * @param parameter
     * @return
     * @throws Exception
     */
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        System.out.println(Thread.currentThread().getName() + " reentrantLock getLock");
        long n = 0;
        for (long i = 0; i < 1000000L; i++) {
            n++;
            System.out.println(Thread.currentThread().getName() + " "
                    + Thread.currentThread().isInterrupted() + " - " + n);
            if (Thread.currentThread().isInterrupted()) {
                // true (返回当前线程的中断状态)
                System.out.println("1>>>>>>>> "
                        + Thread.currentThread().isInterrupted() + " - " + n);
                // true (返回当前线程的中断状态, 同时清除中断状态)
                System.out.println("2>>>>>>>> "
                        + Thread.interrupted() + " - " + n);
                // false (返回当前线程的中断状态)
                System.out.println("3>>>>>>> "
                        + Thread.currentThread().isInterrupted() + " - " + n);
                // false
                System.out.println("4>>>>>>> "
                        + Thread.interrupted() + " - " + n);
                // false
                System.out.println("5>>>>>>> "
                        + Thread.currentThread().isInterrupted() + " - " + n);
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " reentrantLock inLock");
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
