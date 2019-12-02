package com.demo.common.service.thread.lock.concurrent.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterCan {
    LockCan lock = new LockCan();

    //同一个线程的锁对于本线程来说可以获得多次 每次有个count+1进行统计
    public void print() throws InterruptedException {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " start ");
        doAdd();
        System.out.println(Thread.currentThread().getName() + " e n d ");
        lock.unlock();
    }

    public void doAdd() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " aaa ");
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " bbb ");
        lock.unlock();
    }

    public static void main(String args[]) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        cachedThreadPool.execute(() -> {
            try {
                CounterCan counter = new CounterCan();
                counter.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cachedThreadPool.execute(() -> {
            try {
                CounterCan counter = new CounterCan();
                counter.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}