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
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是共享锁, so同一时刻可以多个线程获得锁
 */
public class ReentrantLockRWDemo extends MyExcutor {
    private static ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private static Lock readLock = rwlock.readLock();
    private static Lock writeLock = rwlock.writeLock();

    private static List<String> cacheList = new ArrayList<>();

    /**
     * 读锁和读锁不会阻塞 读锁和写锁会阻塞
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ThreadDemo readThread = new ThreadDemo(this);
        readThread.execute(Params.builder().size(3).type("2").build());

        ThreadDemo writeThread = new ThreadDemo(this);
        writeThread.execute(Params.builder().size(5).build());
        writeThread.futureGet();
    }

    /* 写数据使用写锁 */
    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        while (true) {
            try {
                writeLock.lock(); // 写锁
                System.out.println(Thread.currentThread().getName() + " 写数据: 获得锁");
                Thread.sleep(500);
                String value = new Random().nextInt(100) + "";
                System.out.println(Thread.currentThread().getName() + " 写数据: value=" + value);
                cacheList.add(value);
            } finally {
                writeLock.unlock();
            }

            Thread.sleep(1000);
        }
    }

    /* 读数据使用读锁 */
    @Override
    public Object doExcuteRead(Map<String, Object> parameterparameter) throws Exception {
        while (true) {
            try {
                readLock.lock(); // 读锁
                System.out.println(Thread.currentThread().getName() + " 读数据: 获得锁");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " 读数据: " + cacheList.size() + " >>> " + cacheList);
            } finally {
                readLock.unlock();
            }
        }
    }
}
