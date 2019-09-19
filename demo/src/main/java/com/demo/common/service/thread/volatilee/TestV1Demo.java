package com.demo.common.service.thread.volatilee;

import org.junit.Test;

// https://www.oschina.net/question/3531106_2277522?sort=time
public class TestV1Demo extends Thread {
    private long i = 0;

    // 不加volatile则线程永远都不会停止
    private boolean stop = false;

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

    @Test
    public void test1() throws Exception {
        TestV1Demo demo = new TestV1Demo();
        demo.start();
        demo.setPriority(1);

        System.out.println("--------" + demo.stop);
        Thread.sleep(100);
        demo.stopNow();
        System.out.println(demo.getI());
        System.out.println("--------" + demo.stop);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println(demo.getI());
        }
        System.out.println("done\n");

        System.out.println("--------" + demo.stop);
        demo.stopNow();
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
