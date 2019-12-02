package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.Excutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolatileTest extends Excutor {
    private volatile int number = 0;
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
        System.out.println("结果 " + number);
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ignored) {
        }
        
        synchronized (this) {
            number++;
            number++;
        }
        
        return null;
    }
}