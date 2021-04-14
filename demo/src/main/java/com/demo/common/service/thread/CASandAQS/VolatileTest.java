package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolatileTest extends MyExcutor {
    private volatile int number = 0;
    
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
        System.out.println("结果 " + number);
    }
    
    @Override
    public Object doExcute() throws Exception {
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
