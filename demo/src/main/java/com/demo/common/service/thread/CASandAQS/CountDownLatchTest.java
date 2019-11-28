package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.CASandAQS.abs.Excutor;
import com.demo.common.service.thread.CASandAQS.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 功能等于join()
 */
public class CountDownLatchTest extends Excutor {
    private CountDownLatch latch = new CountDownLatch(8);
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        
        System.out.println("begin");
        boolean result = latch.await(3000, TimeUnit.MILLISECONDS);
        System.out.println("end: " + result + " == > " + latch.getCount());
        
        threadExcutor.futureGet();
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "=====>");
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "==========>");
        } catch (InterruptedException ignored) {
        } finally {
            latch.countDown();
        }
        
        return null;
    }
}
