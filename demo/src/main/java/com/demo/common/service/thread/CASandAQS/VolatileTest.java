package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.CASandAQS.abs.Excutor;
import com.demo.common.service.thread.CASandAQS.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;

public class VolatileTest extends Excutor {
    private volatile int number = 0;
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute();
        System.out.println("结果 " + number);
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {
        }
        
        synchronized (this) {
            number++;
        }
        
        return null;
    }
}
