package com.demo.common.service.thread.lock.other;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreUninterruptiblyTest extends MyExcutor {
    private Semaphore semaphore = new Semaphore(5, true);
    
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
    }
    
    @Override
    public Object doExcute() throws Exception {
        try {
            // acquireUninterruptibly()作用是使等待进入acquire()方法的线程，不允许被中断
            semaphore.acquireUninterruptibly();
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "=====> "
                    + Thread.currentThread().isAlive() + " - "
                    + Thread.currentThread().isInterrupted());
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException ignored) {
        } finally {
            semaphore.release();
        }
        
        return null;
    }
}
