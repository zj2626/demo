package com.demo.common.service.thread.container;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest extends MyExcutor {
    private CyclicBarrier barrier = new CyclicBarrier(8);
    
    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        System.out.println("begin");
        excutorPool.execute(16);
        System.out.println("end: " + barrier.getNumberWaiting() + " == > " + barrier.getParties());
        
        excutorPool.futureGet();
    }
    
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        try {
            TimeUnit.MILLISECONDS.sleep(1000 * new Random().nextInt(2));
            System.out.println(Thread.currentThread().getName() + "=====>");
            int result = barrier.await(3000, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + "====================>" + result);
        } catch (InterruptedException ignored) {
        }
        
        return null;
    }
}
