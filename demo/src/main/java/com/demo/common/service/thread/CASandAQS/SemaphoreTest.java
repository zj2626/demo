package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest extends MyExcutor {
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
            semaphore.acquire();
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "=====> "
                    + Thread.currentThread().isAlive() + " - "
                    + Thread.currentThread().isInterrupted());
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException ignored) {
        } finally{
            semaphore.release();
        }
        
        return null;
    }
}
