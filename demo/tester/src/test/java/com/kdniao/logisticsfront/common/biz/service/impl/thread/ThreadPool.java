package com.kdniao.logisticsfront.common.biz.service.impl.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    /**
     * newCachedThreadPool
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * <p>
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     */
    @Test
    public void test1() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final String info = "first for";
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(info + " >>> " + index);
                }
            });
        }
    }

    /**
     * newFixedThreadPool
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * <p>
     * 线程池大小为2，每个任务输出index后sleep 1秒，所以每两秒打印2个数字
     * <p>
     * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
     */
    @Test
    public void test2() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * newScheduledThreadPool
     * 创建一个定长线程池，支持定时及周期性任务执行
     * <p>
     */
    @Test
    public void test3() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        // 延迟4秒执行
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 4 seconds");
            }
        }, 4, TimeUnit.SECONDS);

        // 延迟5秒开始 定期执行,每1秒一次
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 5 seconds, and excute every 1 seconds");
            }
        }, 5, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * newSingleThreadExecutor
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
     * <p>
     */
    @Test
    public void test4() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
