package com.demo.common.service.thread.abs;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class ExcutorPoolDemo {
    private ExcutorInterface excutor;
    private LockInterface lock;
    private static List<FutureTask> futureList = new ArrayList<>();

    public ExcutorPoolDemo(ExcutorInterface excutor) {
        System.out.println("CPU核心数" + Runtime.getRuntime().availableProcessors());

        this.excutor = excutor;
        if (excutor instanceof LockInterface) {
            this.lock = (LockInterface) excutor;
        }
    }

    public void execute(int size) throws InterruptedException {
        execute(Params.builder().type("doExcute").size(size).build());
    }

    public void execute(Params param) throws InterruptedException {
        lock = Optional.ofNullable(lock).orElse(new DefaultLock());

        Executor service = getExecutor();

        for (int i = 0; i < param.getSize(); i++) {
            if (param.isOrder()) {
                Thread.sleep(2);
            }
            FutureTask<Object> future = new FutureTask<>(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "===>线程开始获取锁===>");
                    if (lock.getLock()) {
                        if (null != param.getType() && param.getType().equals("doExcuteRead")) {
                            return excutor.doExcuteRead();
                        } else {
                            return excutor.doExcute();
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "===>获得锁失败===>");
                        return null;
                    }
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " ERROR");
                    e.printStackTrace();
                    return null;
                } finally {
                    lock.releaseLock();
                }
            });
            futureList.add(future);
        }
        for (FutureTask future : futureList) {
            service.execute((FutureTask) future);
        }
        System.out.println("线程已启动: " + futureList.size());
    }

    public List<FutureTask> getFutureList() {
        return futureList;
    }

    public void futureGet(Long timeOut) {
        for (FutureTask future : futureList) {
            try {
                if (!isCancelled(future)) {
                    future.get(timeOut, TimeUnit.SECONDS);
                }
            } catch (TimeoutException e) {
                System.out.println("* 线程超时等待异常: " + e.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void futureGet() throws InterruptedException {
        for (FutureTask future : futureList) {
            try {
                if (!isCancelled(future)) {
                    future.get();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCancelled(FutureTask future) {
        return future.isCancelled();
    }

    public void futureCancel() throws InterruptedException {
        for (int i = futureList.size() - 1; i >= 0; i--) {
            futureCancel(i);
        }
    }

    public void futureCancel(int num) throws InterruptedException {
        try {
            System.out.println(num + " isCancelled: " + isCancelled(futureList.get(num)));
            futureList.get(num).cancel(true); // true 会中断正在执行的线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // #####################################
    // #####################################
    // #####################################

    // 普通的线程池
    private Executor getExecutor() {
        return Executors.newFixedThreadPool(1000);
    }

    // Spring的线程池
    private Executor getSpringExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("pool-1-thread-");
        executor.initialize();
        return executor;
    }

    // alibaba的线程池
    private Executor getAlibabaExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("pool-1-thread-");
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }
}
