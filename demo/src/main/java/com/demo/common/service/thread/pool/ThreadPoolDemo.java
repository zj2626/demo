package com.demo.common.service.thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    /**
     * 不推荐的使用方式
     * <p>
     * newFixedThreadPool:最后一个参数是工作队列,而其默认阻塞队列大小为Integer.MAX_VALUE,当线程多时耗费资源;(but线程数是固定的)
     * newCachedThreadPool:其线程数最大为Integer.MAX_VALUE,导致创建很多线程(but阻塞队列=最大是固定的)
     * newScheduledThreadPool:其线程数最大为Integer.MAX_VALUE,导致创建很多线程(but阻塞队列=最大是固定的)
     * <p>
     * <p>
     * <p>
     * int corePoolSize,                    核心线程数大小
     * int maximumPoolSize,                 最大线程数大小
     * long keepAliveTime,                  超时(存活)时间,超出核心线程数之外的多余线程的存活时间, 0则立刻回收
     * TimeUnit unit,                       存活时间单位
     * BlockingQueue<Runnable> workQueue,   阻塞队列
     * ThreadFactory threadFactory,         线程配置工厂
     * RejectedExecutionHandler handler     拒绝策略(队列超出最大线程数时)
     *
     * @throws InterruptedException
     */
    @Test
    public void exampleOld() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //        ExecutorService executor = Executors.newCachedThreadPool();
        //        ExecutorService executor = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(1000);
    }

    /**
     * 推荐的使用方法:ScheduledThreadPoolExecutor
     *
     * @throws InterruptedException
     */
    @Test
    public void example1() throws InterruptedException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5,
                new BasicThreadFactory.Builder().namingPattern("zj-example-schedule-pool-%d").daemon(true).build());

        System.out.println(executorService);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(1000);
    }

    /**
     * 推荐的使用方法:ThreadPoolExecutor
     *
     * @throws InterruptedException
     */
    @Test
    public void example2() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                200,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("zj-example-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println(executorService);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        Thread.sleep(1000);
    }

    /**
     * 推荐的使用方法:ThreadPoolTaskExecutor
     *
     * @throws InterruptedException
     */
    @Test
    public void example3() throws InterruptedException {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("example-pool-%d").build());
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);

        executor.initialize();

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(1000);
    }

    @Test
    public void example4() throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(
                5,
                20,
                100L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(30),
                new ThreadFactoryBuilder().setNameFormat("zj-example-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        System.out.println(executorService);

        /**
         * 15 20 25 30 35 40 50 55(ERROR)
         *
         * 由此可得
         *  1.如果核心线程数够用, 就只使用核心线程
         *  2.超出核心线程数的任务, 在阻塞队列中等待
         *  3.阻塞队列也不够用(corePoolSize+workQueue.size()),则启用再启动更多线程,直到总线程数等于最大线程数
         *  4.最大线程数也不足(maximumPoolSize+workQueue.size()),则多余的线程被执行拒绝策略
         */
        for (int i = 0; i < 55; i++) {
            try {
                executorService.execute(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 20; i++) {
            Thread.sleep(2000);
            getInfo(executorService);
        }
    }

    private void getInfo(ThreadPoolExecutor executorService) {
        System.out.println(" 启动的线程数 " + executorService.getLargestPoolSize());
        System.out.println(" 最大线程数 " + executorService.getMaximumPoolSize());
        System.out.println(" 总任务数 " + executorService.getTaskCount());
        System.out.println(" 完成的任务数 " + executorService.getCompletedTaskCount());
        System.out.println(" (待启动的任务)阻塞队列中的任务 " + executorService.getQueue().size());
        System.out.println();
    }
}
