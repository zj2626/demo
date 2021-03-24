package com.demo.common.service.thread.abs;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.*;

public class ExcutorPoolDemo {
    private ExcutorInterface excutor;
    private LockInterface lock;
    private static List<Future> futureList = new ArrayList<>();

    public ExcutorPoolDemo(ExcutorInterface excutor) {
        System.out.println("CPU核心数" + Runtime.getRuntime().availableProcessors());

        this.excutor = excutor;
        if (excutor instanceof LockInterface) {
            this.lock = (LockInterface) excutor;
        }
    }

    public void execute(int size) throws InterruptedException {
        execute(Params.builder().size(size).build());
    }

    public void execute(Params param) throws InterruptedException {
        lock = Optional.ofNullable(lock).orElse(new DefaultLock());

        //        ExecutorService service = Executors.newFixedThreadPool(2);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("pool-1-thread-");
        executor.initialize();
        Executor service = TtlExecutors.getTtlExecutor(executor);

        for (int i = 0; i < param.getSize(); i++) {
            if (param.isOrder()) {
                Thread.sleep(2);
            }
            FutureTask<Object> future = new FutureTask<>(() -> {
                try {
                    if (param.isOrder()) {
                        System.out.println(Thread.currentThread().getName() + "===>线程开始===>");
                    }
                    if (lock.getLock()) {
                        if (StringUtils.isNotEmpty(param.getType()) && param.getType().contains("2")) {
                            return excutor.doExcuteRead(makeRequestParam(param));
                        } else {
                            return excutor.doExcute(makeRequestParam(param));
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
        for (Future future : futureList) {
            service.execute((FutureTask) future);
        }
        System.out.println("线程已启动: " + futureList.size());
    }

    private Map<String, Object> makeRequestParam(Params param) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "wtf");
        parameter.put("skuStatus", "1");
        parameter.put("requestParam", param);
        return parameter;
    }

    public List<Future> getFutureList() {
        return futureList;
    }

    public void futureGet(Long timeOut) {
        for (Future future : futureList) {
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
        for (Future future : futureList) {
            try {
                if (!isCancelled(future)) {
                    future.get();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isCancelled(Future future) {
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
}
