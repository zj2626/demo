package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainWait {
    public static void main(String[] args) throws InterruptedException {

        PrinterNum printerNum = new PrinterNum();

        RunClass6 runClass1 = new RunClass6(printerNum);
        RunClass7 runClass2 = new RunClass7(printerNum);
        RunClass8 runClass3 = new RunClass8(printerNum);
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


class RunClass6 implements Runnable {
    private PrinterNum printerNum;

    public RunClass6(PrinterNum printerNum) {
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

                System.out.println(printerNum.num);
                if (printerNum.num % 3 == 0) {
                    System.out.println(Thread.currentThread().getName() + " <> " + printerNum.num++);
                    printerNum.notifyAll();
                }

                printerNum.wait();
            }
        }
    }
}

class RunClass7 implements Runnable {
    private PrinterNum printerNum;

    public RunClass7(PrinterNum printerNum) {
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

                System.out.println(printerNum.num);
                if (printerNum.num % 3 == 1) {
                    System.out.println(Thread.currentThread().getName() + " <> " + printerNum.num++);
                    printerNum.notifyAll();
                }

                printerNum.wait();
            }
        }
    }
}

class RunClass8 implements Runnable {
    private PrinterNum printerNum;

    public RunClass8(PrinterNum printerNum) {
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

                System.out.println(printerNum.num);
                if (printerNum.num % 3 == 2) {
                    System.out.println(Thread.currentThread().getName() + " <> " + printerNum.num++);
                    printerNum.notifyAll();
                }

                printerNum.wait();
            }
        }
    }
}

class PrinterNum {
    public Integer num = 0;

//    public void printA() throws InterruptedException {
//        synchronized (this) {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(num);
//                if (num % 3 == 0) {
//                    System.out.println(Thread.currentThread().getName() + " <> " + num++);
//                    notifyAll();
//                }
//
//                wait();
//            }
//        }
//    }
//
//    public void printB() throws InterruptedException {
//        synchronized (this) {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(num);
//                if (num % 3 == 1) {
//                    System.out.println(Thread.currentThread().getName() + " <> " + num++);
//                    notifyAll();
//                }
//
//                wait();
//            }
//        }
//    }
//
//    public void printC() throws InterruptedException {
//        synchronized (this) {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(num);
//                if (num % 3 == 2) {
//                    System.out.println(Thread.currentThread().getName() + " <> " + num++);
//                    notifyAll();
//                }
//
//                wait();
//            }
//        }
//    }
}

