package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainRotation {
    public static void main(String[] args) {
        PrintWay way = new PrintWay();

        PrintA printA = new PrintA(way);
        PrintB printB = new PrintB(way);
        PrintC printC = new PrintC(way);

        Thread t1 = new Thread(printA);
        Thread t2 = new Thread(printB);
        Thread t3 = new Thread(printC);

        t1.start();
        t2.start();
        t3.start();
    }
}

class PrintA implements Runnable {
    private PrintWay way;

    public PrintA(PrintWay way) {
        this.way = way;
    }

    @Override
    public void run() {
        try {
            way.printA();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PrintB implements Runnable {
    private PrintWay way;

    public PrintB(PrintWay way) {
        this.way = way;
    }

    @Override
    public void run() {
        try {
            way.printB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PrintC implements Runnable {
    private PrintWay t;

    PrintC(PrintWay t) {
        this.t = t;
    }

    @Override
    public void run() {
        try {
            t.printC();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class PrintWay {
    private Integer flag = 0;

    public void printA() throws InterruptedException {
        synchronized (this) {
            while (true) {
                Thread.sleep(500);

                System.out.println(flag);
                if (flag == 0) {
                    System.out.println("A");
                    flag = 1;
                    notifyAll();
                }
                wait();
            }
        }
    }

    public void printB() throws InterruptedException {
        synchronized (this) {
            while (true) {
                Thread.sleep(500);

                System.out.println(flag);
                if (flag == 1) {
                    System.out.println("B");
                    flag = 2;
                    notifyAll();
                }
                wait();
            }
        }
    }

    public void printC() throws InterruptedException {
        synchronized (this) {
            while (true) {
                Thread.sleep(500);

                System.out.println(flag);
                if (flag == 2) {
                    System.out.println("C");
                    flag = 0;
                    notifyAll();
                }
                wait();
            }
        }
    }
}
