package com.demo.common.service.thread.lock;

import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedDemo extends MyExcutor implements LockInterface {
    private static Integer lock = 0;
    private static int count = 0;

    @Test
    public void test() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(20);
        threadExcutor.futureGet();
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
    public String doExcute(Map<String, String> parameter) throws Exception {
        synchronized(lock){
            for (int i = 0; i < 50; i++) {
                Thread.sleep(1);
                count++;
            }
        }
        return null;
    }
}
