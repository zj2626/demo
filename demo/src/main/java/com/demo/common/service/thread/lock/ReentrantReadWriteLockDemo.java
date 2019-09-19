package com.demo.common.service.thread.lock;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是共享锁, so同一时刻可以多个线程获得锁
 */
public class ReentrantReadWriteLockDemo {
    private static Map<String, String> cacheMap = new HashMap<>();
    private static int count = 0;
    private static ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private static Lock readLock = rwlock.readLock();
    private static Lock writeLock = rwlock.writeLock();

    /**
     * 读锁和读锁不会阻塞 读锁和写锁会阻塞
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ReentrantReadWriteLockDemo.writeData("a", "abc");
        Thread.sleep(200);

        new Thread(() -> ReentrantReadWriteLockDemo.readData("a")).start();
        new Thread(() -> ReentrantReadWriteLockDemo.writeData("b", "acd")).start();

        Thread.sleep(2000);
        System.out.println(count);
    }

    /* 读数据使用读锁 */
    public static String readData(String key) {
        try {
            readLock.lock(); // 读锁
            System.out.println("read");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return cacheMap.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /* 写数据使用写锁 */
    public static void writeData(String key, String value) {
        try {
            writeLock.lock(); // 写锁
            System.out.println("write");

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }

    }
}
