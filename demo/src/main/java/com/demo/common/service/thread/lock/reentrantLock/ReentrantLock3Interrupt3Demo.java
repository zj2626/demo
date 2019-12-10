package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock3Interrupt3Demo extends MyExcutor implements LockInterface {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * ReentrantLock.lockInterruptibly允许在等待时由其它线程调用等待线程的Thread.interrupt方法来中断等待线程的等待而直接返回，
     * 这时不用获取锁，而会抛出一个InterruptedException。
     * <p>
     * ReentrantLock.lock方法不允许Thread.interrupt中断,
     * 即使检测到Thread.isInterrupted,一样会继续尝试获取锁，失败则继续休眠。
     * 只是在最后获取锁成功后再把当前线程置为interrupted状态,然后再中断线程。
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
     * lock
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
        reentrantLock.lock();
        return true;
    }

    @Override
    public void releaseLock() {
        if (reentrantLock.isLocked() && reentrantLock.isHeldByCurrentThread()) {
            reentrantLock.unlock();
        }
    }
}
