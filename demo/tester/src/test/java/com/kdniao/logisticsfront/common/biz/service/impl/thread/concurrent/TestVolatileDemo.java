package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

public class TestVolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

//        Thread.sleep(1000);
        while (true) {
            boolean isFlag = td.isFlag();
            if (isFlag) {
                System.out.println("-----------");
                break;
            }
//            System.out.println(td.isFlag());
        }
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;

    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}