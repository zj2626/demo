package com.demo.common.service.thread.synchronizedLock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

public class SynchronizedDemo extends MyExcutor implements LockInterface {
    private static Integer lock = 0;
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(20);
        excutorPool.futureGet();
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
    public Object doExcute() throws Exception {
        synchronized(lock){
            for (int i = 0; i < 50; i++) {
                Thread.sleep(1);
                count++;
            }
        }
        return null;
    }
}
