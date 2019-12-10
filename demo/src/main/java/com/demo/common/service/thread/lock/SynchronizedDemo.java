package com.demo.common.service.thread.lock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.Map;

public class SynchronizedDemo extends MyExcutor implements LockInterface {
    private static Integer lock = 0;
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
        calculate(50L * 1 * 20);
        System.out.println(count);
    }

    @Override
    public boolean getLock() {
        return true;
    }

    @Override
    public void releaseLock() {
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        synchronized(lock){
            for (int i = 0; i < 50; i++) {
                Thread.sleep(1);
                count++;
            }
        }
        return null;
    }
}
