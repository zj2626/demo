package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Volatile2Test extends MyExcutor {
    private volatile boolean ifRun = true;
    private long number = 0;
    
    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(2);
        
        stopThread();
        
        excutorPool.futureGet();
        System.out.println("结果 " + ifRun + " - " + number);
    }
    
    @Override
    public Object doExcute() throws Exception {
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
