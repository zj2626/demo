package com.demo.common.service.thread.lock.other;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 功能等于join()
 */
public class CountDownLatchTest extends MyExcutor {
    private CountDownLatch latch = new CountDownLatch(8);
    
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(16);
        
        System.out.println("begin");
        boolean result = latch.await(3000, TimeUnit.MILLISECONDS);
        System.out.println("end: " + result + " == > " + latch.getCount());
        
        excutorPool.futureGet();
    }
    
    @Override
    public Object doExcute() throws Exception {
        try {
            TimeUnit.MILLISECONDS.sleep(1000 * new Random().nextInt(2));
            System.out.println(Thread.currentThread().getName() + "=====>");
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "====================>");
        } catch (InterruptedException ignored) {
        } finally {
            latch.countDown();
        }
        
        return null;
    }
}
