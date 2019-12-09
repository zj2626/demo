package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest extends MyExcutor {
    private AtomicInteger number = new AtomicInteger(0);
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
        System.out.println("结果 " + number.get());
    }
    
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        
        number.getAndIncrement();
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ignored) {
        }
        number.incrementAndGet();
        
        return null;
    }
    
    @Test
    public void other() {
    
    }
}
