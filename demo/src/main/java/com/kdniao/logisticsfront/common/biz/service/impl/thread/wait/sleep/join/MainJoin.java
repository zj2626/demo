package com.kdniao.logisticsfront.common.biz.service.impl.thread.wait.sleep.join;

public class MainJoin {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();

        RunClass3 runClass1 = new RunClass3(o);
        Thread t1 = new Thread(runClass1);
//        t1.setDaemon(true);
        t1.start();
        System.out.println("AAAAAAA");
        t1.join();
        System.out.println("BBBBB ");

    }

    static class RunClass3 implements Runnable {
        private Object u;

        public RunClass3(Object u) {
            this.u = u;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("in " + Thread.currentThread().getName());
            }
        }
    }

}
