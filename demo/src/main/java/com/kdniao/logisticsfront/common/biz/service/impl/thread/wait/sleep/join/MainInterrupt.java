package com.kdniao.logisticsfront.common.biz.service.impl.thread.wait.sleep.join;

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

    /**
     * Thread.interrupt()方法不会中断一个正在运行的线程。
     * 这一方法实际上完成的是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态。
     * 更确切的说，如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，那么，它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态
     */
    static class RunClass implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interruted!");
                    // Thread.interrupt()方法不会中断一个正在运行的线程,这里通过break结束循环来结束线程
                    break;
                }

                System.out.println(" running ...");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    //设置中断状态，抛出异常后会清除中断标记位
                    Thread.currentThread().interrupt();
                }

                Thread.yield();
            }
        }
    }
}

