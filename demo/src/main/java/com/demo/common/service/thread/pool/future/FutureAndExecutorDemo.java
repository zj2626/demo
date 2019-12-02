package com.demo.common.service.thread.pool.future;

import org.junit.Test;

import java.util.concurrent.*;

public class FutureAndExecutorDemo {
    private static CallableImpl callableImpl = new CallableImpl();
    private static ExecutorService executor = Executors.newCachedThreadPool();

    @Test
    public void test() {
        /**
         * 第一种方式:Future + ExecutorService
         */
        Future<Integer> future
                = executor.submit(callableImpl);
        futureOption(future);

        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();
    }

    @Test
    public void test2() {
        /**
         * 第二种方式: FutureTask + ExecutorService
         */
        FutureTask<Integer> futureTask = new FutureTask<>(callableImpl);
        executor.submit(futureTask);
        futureOption(futureTask);

        // 停止接收新任务，原来的任务继续执行
        executor.shutdown();
    }

    private void futureOption(Future<Integer> future) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 调用isDone()判断任务是否结束
        if (!future.isDone()) {
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
            result = future.get(1, TimeUnit.SECONDS);
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
            Thread.sleep(3000);
            return 1;
        }
    }
}