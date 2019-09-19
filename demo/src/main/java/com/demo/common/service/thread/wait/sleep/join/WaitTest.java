package com.demo.common.service.thread.wait.sleep.join;

public class WaitTest {
    public int flag = 10;

    public static void main(String[] args) {
        WaitTest wait = new WaitTest();

        new Thread(new Runnable() {
            public void run() {
                wait.method1();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                wait.method2();
            }
        }).start();
    }

    public void method1() {
        System.out.println("method 1 is running");
        try {
            synchronized (this) {
                System.out.println("method 1 is into lock");
                while (flag > 0) {
                    this.wait();
                    System.out.println("method 1 get notify");
                }
                Thread.sleep(1500);
                System.out.println("method 1 is out lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method 1 is end");
    }

    public void method2() {
        System.out.println("method 2 is running");
//        synchronized (this) {
//            System.out.println("method 2 is into lock");
        while (flag > 0) {
            try {
                synchronized (this) {
                    flag--;
                    this.notify();
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }
        }
        System.out.println("method 2 is end");
    }
}