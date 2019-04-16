package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainYield2 {
    public static void main(String[] args) throws InterruptedException {

        PrinterNum printerNum = new PrinterNum();

        RunClass9 runClass1 = new RunClass9(printerNum);
        RunClass10 runClass2 = new RunClass10(printerNum);
        RunClass11 runClass3 = new RunClass11(printerNum);
        Thread t1 = new Thread(runClass1);
        Thread t2 = new Thread(runClass2);
        Thread t3 = new Thread(runClass3);
        t1.start();
        t2.start();
        t3.start();

//        t1.wait();
//        t2.wait();

//        t1.join();
//        t2.join();

    }
}


class RunClass9 implements Runnable {
    private PrinterNum printerNum;

    public RunClass9(PrinterNum printerNum) {
        this.printerNum = printerNum;
    }

    @Override
    public void run() {
        try {
            fun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fun() throws InterruptedException {
//        printerNum.printA();

        synchronized (printerNum) {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("RunClass9 " + printerNum.num);
                if (printerNum.num % 3 == 0) {
                    System.out.println("RunClass9 " + Thread.currentThread().getName() + " <> " + printerNum.num++);
                    Thread.yield();
                }

                System.out.println("\nRunClass9 after wait");
            }
        }
    }
}

class RunClass10 implements Runnable {
    private PrinterNum printerNum;

    public RunClass10(PrinterNum printerNum) {
        this.printerNum = printerNum;
    }

    @Override
    public void run() {
        try {
            fun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fun() throws InterruptedException {
//        printerNum.printB();

        synchronized (printerNum) {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("RunClass10 " + printerNum.num);
                if (printerNum.num % 3 == 1) {
                    System.out.println("RunClass10 " + Thread.currentThread().getName() + " <> " + printerNum.num++);
                    Thread.yield();
                }

                System.out.println("\nRunClass10 after wait");
            }
        }
    }
}

class RunClass11 implements Runnable {
    private PrinterNum printerNum;

    public RunClass11(PrinterNum printerNum) {
        this.printerNum = printerNum;
    }

    @Override
    public void run() {
        try {
            fun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fun() throws InterruptedException {
//        printerNum.printC();

        synchronized (printerNum) {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("RunClass11 " + printerNum.num);
                if (printerNum.num % 3 == 2) {
                    System.out.println("RunClass11 " + Thread.currentThread().getName() + " <> " + printerNum.num++);
                    Thread.yield();
                }

                System.out.println("\nRunClass11 after wait");
            }
        }
    }
}

