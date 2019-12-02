package com.demo.common.service.thread.callable;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) {
        MyCallable callable1 = new MyCallable(1000);                       // 要执行的任务
        MyCallable callable2 = new MyCallable(2000);
        MyCallable callable3 = new MyCallable(3000);
        MyCallable callable4 = new MyCallable(4000);

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);// 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);
        FutureTask<String> futureTask4 = new FutureTask<>(callable4);

        ExecutorService executor = Executors.newFixedThreadPool(3);        // 创建线程池并返回ExecutorService实例
        executor.execute(futureTask1);
        executor.execute(futureTask2);
        Future<String> future3 = executor.submit(callable3);
        new Thread(futureTask4).start();

        try {
            System.out.println("得到FutureTask1结果: " + futureTask1.get(1L, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("得到FutureTask2结果: " + futureTask2.get(1L, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("得到Future3结果: " + future3.get(1L, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("得到futureTask4结果: " + futureTask4.get(1L, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    private static class MyCallable implements Callable<String> {
        private long waitTime;

        public MyCallable(int timeInMillis) {
            this.waitTime = timeInMillis;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " -#######- ");
            Thread.sleep(waitTime);
            return Thread.currentThread().getName();
        }
    }
}
