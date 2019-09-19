package com.demo.common.service.thread.pool;

import java.util.concurrent.*;

public class FutureAndExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableImpl callableImpl = new CallableImpl();
        ExecutorService executor = Executors.newCachedThreadPool();

        /**
         * 第一种方式:Future + ExecutorService
         */
//        Future<Integer> futureTask = executor.submit(callableImpl);

        /**
         * 第二种方式: FutureTask + ExecutorService
         */
        FutureTask<Integer> futureTask = new FutureTask<>(callableImpl);
        executor.submit(futureTask);

        /**
         * 第三种方式:FutureTask + Thread
         */
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableImpl);
//        Thread thread = new Thread(futureTask);
//        thread.setName("CallableImpl thread");
//        thread.start();

        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");

        // 4. 调用isDone()判断任务是否结束
        if (!futureTask.isDone()) {
            System.out.println("CallableImpl is not done");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int result = 0;
        try {
            // 5. 调用get()方法获取任务结果,如果任务没有执行完成则阻塞等待
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result is " + result);

    }

    // 继承Callable接口,实现call()方法,泛型参数为要返回的类型
    static class CallableImpl implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("Thread [" + Thread.currentThread().getName() + "] is running");
            int result = 0;
            for (int i = 0; i < 100; ++i) {
                result += i;
            }

            Thread.sleep(3000);
            return result;
        }
    }
}