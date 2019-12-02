package com.demo.common.service.thread.CASandAQS.volatilee;

import org.junit.Test;

public class TestV2Demo extends Thread {
    private long i = 0;

    // 不加volatile则线程永远都不会停止
//    private Switch aSwitch = new Switch();
    private volatile Switch aSwitch = new Switch();

    public void stopNow() {
        aSwitch.setStop(true);
    }

    public long getI() {
        return i;
    }

    @Override
    public void run() {
        while (!aSwitch.isStop()) {
            i++;
        }
        System.out.println("Stoped at " + i);
    }

    @Test
    public void test1() throws Exception {
        TestV2Demo demo = new TestV2Demo();
        demo.start();

        System.out.println("a--------" + demo.aSwitch.isStop());
        Thread.sleep(100);
        demo.stopNow();
        System.out.println(demo.getI());
        System.out.println("b--------" + demo.aSwitch.isStop());

        for (int i = 0; i < 20; i++) {
            Thread.sleep(100);
            System.out.println(demo.getI());
        }

        System.out.println("before");
        demo.join();
        System.out.println("after");
    }

    public class Switch{
        private boolean stop = false;
//        private volatile boolean stop = false;

        public boolean isStop() {
            return stop;
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }
    }
}
