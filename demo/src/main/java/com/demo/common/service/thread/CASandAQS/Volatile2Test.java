package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Volatile2Test extends MyExcutor {
    private volatile boolean ifRun = true;
    private long number = 0;
    
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(2);
        
        stopThread();
        
        threadExcutor.futureGet();
        System.out.println("结果 " + ifRun + " - " + number);
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        while (ifRun) {
            number++;
        }
        return null;
    }
    
    private void stopThread(){
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ignored) {
        }
        
        ifRun = false;
    }
}
