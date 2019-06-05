package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 500; i++) {
            new Thread(ad).start();
        }

        VolatileAtomicDemo nad = new VolatileAtomicDemo();
        for (int i = 0; i < 500; i++) {
            new Thread(nad).start();
        }

        NormalAtomicDemo mad = new NormalAtomicDemo();
        for (int i = 0; i < 500; i++) {
            new Thread(mad).start();
        }

        Thread.sleep(3000);

        System.out.println(ad.serialNumber);
        System.out.println(nad.serialNumber);
        System.out.println(mad.serialNumber);
    }

    static class NormalAtomicDemo implements Runnable {

        private int serialNumber = 0;

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }

            synchronized (this) { // 必须有
                serialNumber++;
            }

            synchronized (this) { // 必须有
                serialNumber++;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }

    }

    static class VolatileAtomicDemo implements Runnable {

        private volatile int serialNumber = 0;

        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }

            synchronized (this) { // 必须有
                serialNumber++;
            }

            synchronized (this) { // 必须有
                serialNumber++;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }

    }

    static class AtomicDemo implements Runnable {

        private AtomicInteger serialNumber = new AtomicInteger(0);

        @Override
        public void run() {

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }

            /*way one*/
            serialNumber.getAndIncrement(); // 方法是返回旧值（即加1前的原始值）
            serialNumber.incrementAndGet(); // 方法是返回的是新值（即加1后的值）

            /*way two*/
//            synchronized (this) { // 必须有
//                serialNumber.set(serialNumber.get() + 1);
//            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
    }
}

