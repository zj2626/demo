package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.CASandAQS.abs.Excutor;
import com.demo.common.service.thread.CASandAQS.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreUninterruptiblyTest extends Excutor {
    private Semaphore semaphore = new Semaphore(5, true);
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
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
