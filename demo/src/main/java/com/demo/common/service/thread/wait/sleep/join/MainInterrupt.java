package com.demo.common.service.thread.wait.sleep.join;

public class MainInterrupt {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunClass2());
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupt");
        thread.interrupt(); // set true
    }

    /**
     * Thread.interrupt()方法不会中断一个正在运行的线程。
     * 这一方法实际上完成的是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态。
     * 更确切的说，如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，那么，它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态
     */
    static class RunClass implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(" running ... " + Thread.currentThread().isInterrupted()); // false
            }
            System.out.println("Interruted! " + Thread.currentThread().isInterrupted()); // true
        }
    }

    static class RunClass2 implements Runnable {

        @Override
        public void run() {
            // ClearInterrupted为true时，中断状态会被重置，为false则不会被重置
            while (!Thread.interrupted()) {
                System.out.println(" running ... " + Thread.currentThread().isInterrupted()); // false
            }
            System.out.println("Interruted! " + Thread.currentThread().isInterrupted()); // false
        }
    }
}

