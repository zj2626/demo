package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainSleep {
    public static void main(String[] args) throws InterruptedException {

        RunClass5 runClass1 = new RunClass5("r1");
        RunClass5 runClass2 = new RunClass5("r2");
        Thread t1 = new Thread(runClass1);
        Thread t2 = new Thread(runClass2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

}

class RunClass5 implements Runnable {
    private static Integer num = 5;
    private String u;

    public RunClass5(String u) {
        this.u = u;
    }

    @Override
    public void run() {
        synchronized (num) {
            while (true) {
                System.out.println(u + " -in- " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
