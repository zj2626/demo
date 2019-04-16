package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainInterrupt {
    public static void main(String[] args) {
        RunClass runClass = new RunClass();
        Thread thread = new Thread(runClass);
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupt");
        thread.interrupt();
    }

}

class RunClass implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interruted!");
                break;
            }

            try {
                System.out.println(" running ...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();

                //设置中断状态，抛出异常后会清除中断标记位
                Thread.currentThread().interrupt();
            }

            Thread.yield();
        }
    }
}
