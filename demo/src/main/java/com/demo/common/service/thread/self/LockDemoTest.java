package com.demo.common.service.thread.self;

import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import com.demo.common.service.thread.abs.LockInterface;
import com.demo.common.service.thread.abs.MyExcutor;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemoTest extends MyExcutor implements LockInterface {
    private static int count = 0;
    private static ReentrantLock lock0 = new ReentrantLock();
    private static MyLock lock = new MyLock();

    @Test
    public void test() throws InterruptedException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet();
        System.out.println(count);
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            count++;
        }
        return null;
    }

    @Override
    public boolean getLock() {
        System.out.println(Thread.currentThread().getName() + " ====> getLock");
        lock.lock();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        return true;
    }

    @Override
    public void releaseLock() {
        System.out.println(Thread.currentThread().getName() + " ====> releaseLock");
        lock.unlock();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
