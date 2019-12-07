package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁和非公平锁 TODO ??? 没效果
 */
public class ReentrantLockFairDemo extends MyExcutor {
    // true:公平或 false(默认):非公平
    private static ReentrantLock fairLock = new ReentrantLock(true);
    private static ReentrantLock unfairLock = new ReentrantLock();
    private static int count = 0;

    @Test
    public void testFair() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(Params.builder().size(30).isOrder(true).build());
        threadExcutor.futureGet();
        System.out.println(count);
    }

    @Test
    public void testUnfair() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(Params.builder().size(30).type("2").isOrder(true).build());
        threadExcutor.futureGet();
        System.out.println(count);
    }

    /* 公平锁 */
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            fairLock.lock();
            System.out.println(Thread.currentThread().getName() + " 公平锁");
            Thread.sleep(200);
            count++;
        } finally {
            fairLock.unlock();
        }
        return null;
    }

    /* 非公平锁 */
    @Override
    public String doExcuteRead(Map<String, String> parameter) throws Exception {
        try {
            unfairLock.lock();
            System.out.println(Thread.currentThread().getName() + " 非公平锁");
            Thread.sleep(200);
            count++;
        } finally {
            unfairLock.unlock();
        }
        return null;
    }
}