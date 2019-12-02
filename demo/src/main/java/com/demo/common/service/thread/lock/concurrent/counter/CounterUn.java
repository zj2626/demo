package com.demo.common.service.thread.lock.concurrent.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterUn {
    LockUn lock = new LockUn();

    //当调用print()方法时，获得了锁，这时就无法再调用doAdd()方法，这时必须先释放锁才能调用，所以称这种锁为不可重入锁，也叫自旋锁。
    //同一个线程的锁只能获得一次
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
                CounterUn counter = new CounterUn();
                counter.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        cachedThreadPool.execute(() -> {
//            try {
//                CounterUn counter = new CounterUn();
//                counter.print();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }
}