package com.demo.common.service.thread.abs;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.*;

public class ThreadDemo {
    private MyExcutor excutor;
    private LockInterface lock;
    private static List<Future> futureList = new ArrayList<>();

    public ThreadDemo(MyExcutor excutor) {
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
                            return excutor.doExcuteRead(makeRequestParam());
                        } else {
                            return excutor.doExcute(makeRequestParam());
                        }
                    }else{
                        System.out.println(Thread.currentThread().getName() + "===>获得锁失败===>");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    lock.releaseLock();
                }
            });
            service.execute(future);
            futureList.add(future);
        }
    }

    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "wtf");
        parameter.put("skuStatus", "1");
        return parameter;
    }

    public void futureGet() throws InterruptedException {
        for (Future future : futureList) {
            try {
                if(!future.isCancelled()) {
                    future.get();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void futureCancel() throws InterruptedException {
        for (Future future : futureList) {
            try {
                future.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
