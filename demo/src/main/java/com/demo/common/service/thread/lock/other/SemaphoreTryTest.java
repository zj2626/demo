package com.demo.common.service.thread.lock.other;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTryTest extends MyExcutor {
    private Semaphore semaphore = new Semaphore(5, true);
    
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
    }
    
    @Override
    public Object doExcute() throws Exception {
        boolean tryAcquire = false;
        try {
            tryAcquire = semaphore.tryAcquire(1, 2000, TimeUnit.MILLISECONDS);
            if (tryAcquire) {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println(Thread.currentThread().getName() + "=====>");
                TimeUnit.MILLISECONDS.sleep(500);
            } else {
                System.out.println(Thread.currentThread().getName() + " < < ");
            }
        } catch (InterruptedException ignored) {
        } finally {
            if (tryAcquire) {
                semaphore.release();
            }
        }
        
        return null;
    }
}
