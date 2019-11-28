package com.demo.common.service.thread.CASandAQS;

import com.demo.common.service.thread.CASandAQS.abs.Excutor;
import com.demo.common.service.thread.CASandAQS.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;

public class AtomicTest extends Excutor {
    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute();
    }
    
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
    
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        
        return null;
    }
}
