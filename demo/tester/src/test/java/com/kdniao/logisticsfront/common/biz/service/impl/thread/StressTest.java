package com.kdniao.logisticsfront.common.biz.service.impl.thread;

public class StressTest {
    public static void main(String args[]) {
        MyThread myThread = new MyThread();
        for (int i = 0; i < 100000; i++) {
            new Thread(myThread).start();
        }
    }

}

class MyThread implements Runnable {
    int num;

    @Override
    public void run() {
        invoke();
    }

    public void invoke() {
        for (int i = 0; i < 1000000; i++) {
            num++;
            System.out.println(num);
        }
    }
}