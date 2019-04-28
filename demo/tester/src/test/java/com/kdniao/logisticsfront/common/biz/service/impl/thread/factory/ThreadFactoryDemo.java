package com.kdniao.logisticsfront.common.biz.service.impl.thread.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

public class ThreadFactoryDemo {
    /**
     * 不推荐的使用方式
     * <p>
     * newFixedThreadPool:最后一个参数是工作队列,而其默认阻塞队列大小为Integer.MAX_VALUE,当线程多时耗费资源;(but线程数是固定的)
     * newCachedThreadPool:其线程数最大为Integer.MAX_VALUE,导致创建很多线程(but阻塞队列=最大是固定的)
     * newScheduledThreadPool:其线程数最大为Integer.MAX_VALUE,导致创建很多线程(but阻塞队列=最大是固定的)
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
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("zj-example-pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

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
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("example-pool-%d").build();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(threadFactory);
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
}
