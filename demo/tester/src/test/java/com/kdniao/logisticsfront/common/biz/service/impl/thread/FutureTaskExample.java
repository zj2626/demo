package com.kdniao.logisticsfront.common.biz.service.impl.thread;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskExample {
    public static void main(String[] args) {
        MyCallable callable1 = new MyCallable(1000);                       // 要执行的任务
        MyCallable callable2 = new MyCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);// 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(3);        // 创建线程池并返回ExecutorService实例
        executor.execute(futureTask1);  // 执行任务
        executor.execute(futureTask2);

        int i = 1;
        while (true) {
            i++;
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {//  两个任务都完成
                    System.out.println("Done");
                    executor.shutdown();                          // 关闭线程池和服务
                    return;
                }

                if (!futureTask1.isDone()) { // 任务1没有完成，get()导致阻塞 会等待，直到任务完成
                    System.out.println("FutureTask1执行中 此时调用get()则main线程阻塞");
//                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }
//
//                if(i >= 65){
//                    futureTask2.cancel(false); // i=65时候取消线程2 (false:不允许在线程运行时中断)
//                }
//
                System.out.println("等待FutureTask2中 " + new Date());

                System.out.println("得到FutureTask2结果: " + futureTask2.get(1L, TimeUnit.SECONDS));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
