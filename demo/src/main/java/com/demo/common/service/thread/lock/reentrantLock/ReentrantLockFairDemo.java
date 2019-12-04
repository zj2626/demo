package com.demo.common.service.thread.lock.reentrantLock;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是共享锁, so同一时刻可以多个线程获得锁
 */
public class ReentrantLockFairDemo extends MyExcutor {
    // true:公平或 false(默认):非公平
    private static ReentrantLock fairLock = new ReentrantLock(true);
    private static ReentrantLock unfairLock = new ReentrantLock(false);
    private static int count = 0;

    /**
     * 读锁和读锁不会阻塞 读锁和写锁会阻塞
     *
     * @throws InterruptedException
     */
    @Test
    public void testFair() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(Params.builder().size(50).build());
        threadExcutor.futureGet();
        System.out.println(count);
    }

    @Test
    public void testUnfair() throws InterruptedException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(Params.builder().size(50).type("2").build());
        threadExcutor.futureGet();
        System.out.println(count);
    }

    /* 公平锁 */
    @Override
    public String doExcute(Map<String, String> parameter) throws Exception {
        try {
            fairLock.lock();
            Thread.sleep(1);
            System.out.println(Thread.currentThread().getName() + " 公平锁");
            count++;
        } finally {
            fairLock.unlock();
        }

        Thread.sleep(100);
        return null;
    }

    /* 非公平锁 */
    @Override
    public String doExcuteRead(Map<String, String> parameter) throws Exception {
        try {
            unfairLock.lock(); // 读锁
            System.out.println(Thread.currentThread().getName() + " 非公平锁");
            Thread.sleep(1);
            count++;
        } finally {
            unfairLock.unlock();
        }
        Thread.sleep(100);
        return null;
    }
}
