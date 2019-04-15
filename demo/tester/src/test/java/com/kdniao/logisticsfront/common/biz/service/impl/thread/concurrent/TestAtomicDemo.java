package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 500; i++) {
            new Thread(ad).start();
        }
//
//        NotAtomicDemo nad = new NotAtomicDemo();
//        for (int i = 0; i < 500; i++) {
//            new Thread(nad).start();
//        }

//        NormalAtomicDemo mad = new NormalAtomicDemo();
//        for (int i = 0; i < 500; i++) {
//            new Thread(mad).start();
//        }
    }

}

class NormalAtomicDemo implements Runnable {

    private int serialNumber = 0;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println("NormalAtomicDemo " + serialNumber++);
    }

}

class NotAtomicDemo implements Runnable {

    private volatile int serialNumber = 0;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println("NotAtomicDemo " + serialNumber++);
    }

}

class AtomicDemo implements Runnable {

    private AtomicInteger serialNumber = new AtomicInteger(0);

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        //i++ 实际是int temp=i;i=i+1;i=temp; 需要原子性操作
        System.out.println("AtomicDemo " + serialNumber.getAndIncrement());
    }
}