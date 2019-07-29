package com.kdniao.logisticsfront.common.biz.service.impl.thread.volatilee;

public class TestVDemo extends Thread {
    private long i = 0;

    // 不加volatile则线程永远都不会停止
    private boolean stop = false;
//    private volatile boolean stop = false;

    public void stopNow() {
        stop = true;
    }

    public long getI() {
        return i;
    }

    @Override
    public void run() {
        while (!stop) {
            i++;
            for (int i = 0; i < 100000; i++) {
                int k = i * 1234;
            }
        }
        System.out.println("Stoped at " + i);
    }

    public static void main(String[] args) throws InterruptedException {
        TestVDemo demo = new TestVDemo();
        demo.start();

        System.out.println("--------" + demo.stop);
        Thread.sleep(100);
        demo.stopNow();
        System.out.println(demo.getI());
        System.out.println("--------" + demo.stop);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println(demo.getI());
        }

        System.out.println("before");
        demo.join();
        System.out.println("after");
    }

}
