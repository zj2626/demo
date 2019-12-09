package com.demo.common.service.thread.abs;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.*;

public class ThreadDemo {
    private MyExcutor excutor;
    private LockInterface lock;
    private static List<Future> futureList = new ArrayList<>();

    public ThreadDemo(MyExcutor excutor) {
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
        ExecutorService service = Executors.newFixedThreadPool(300);

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
            service.execute(future);
            futureList.add(future);
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

    public List<Future> getFutureList(){
        return futureList;
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
            futureList.get(num).cancel(true); // true 会中断正在执行的线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
