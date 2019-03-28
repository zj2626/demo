package com.kdniao.logisticsfront.common.biz.service.impl.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainTest {
    public static void main(String[] args) throws Exception {
        System.out.println("do some");

        System.out.println("--------------------------------------------------1 Callable");

        ExecutorService executor = Executors.newCachedThreadPool();
        CallDemo<String> callDemo = new CallDemo<>();
        Future<String> future = executor.submit(callDemo);

        if (future.isDone()) {
            System.out.println("异步任务完成");
        } else {
            System.out.println("异步任务进行中");
        }

        String result = future.get();
//        String result = future.get(2, TimeUnit.SECONDS);
        System.out.println("得到异步任务返回结果：" + result);

        System.out.println("--------------------------------------------------2 FutureTask + Callable");

        FutureTaskDemo<String> futureTaskDemo = new FutureTaskDemo<>(callDemo);
        new Thread(futureTaskDemo).start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Main " + i);
            if (i > 4) {
                futureTaskDemo.cancel(true); // false 不中断线程只修改task的状态为cancelled
            }
            Thread.sleep(500);
        }

        System.out.println("isDone " + futureTaskDemo.isDone());
        System.out.println("isCancelled " + futureTaskDemo.isCancelled());
        if (futureTaskDemo.isDone() && !futureTaskDemo.isCancelled()) {
            result = futureTaskDemo.get();
            System.out.println("得到异步任务返回结果：" + result);
        }

        System.out.println("--------------------------------------------------3 FutureTask + Runnable");
        String runResult = null;
        FutureTaskDemo<String> futureTaskDemo2 = new FutureTaskDemo<>(new RunDemo(), runResult);
        Future<?> future2 = executor.submit(futureTaskDemo2);
        System.out.println(futureTaskDemo2.get());
        System.out.println(future2.get());
        System.out.println(runResult);

        System.out.println("--------------------------------------------------4 FutureTask + RunnableFuture");
        FutureTaskDemo<String> futureTaskDemo3 = new FutureTaskDemo<>(new RunnableFutureDemo(), runResult);
        Future<?> future3 = executor.submit(futureTaskDemo3);
        System.out.println(futureTaskDemo3.get());
        System.out.println(future3.get());
        System.out.println(runResult);

        System.out.println("--------------------------------------------------5 ");

        System.out.println("--------------------------------------------------6 ");
    }
}
