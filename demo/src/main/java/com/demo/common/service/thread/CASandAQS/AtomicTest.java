package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.CASandAQS.abs.Excutor;
import com.demo.common.service.thread.CASandAQS.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest extends Excutor {
    private AtomicInteger number = new AtomicInteger(0);
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute();
        threadExcutor.futureGet();
        System.out.println("结果 " + number.get());
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        
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
