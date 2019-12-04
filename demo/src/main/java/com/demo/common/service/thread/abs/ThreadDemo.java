package com.demo.common.service.thread.abs;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {
    private MyExcutor excutor;
    private LockInterface lock;
    private static List<Future> futureList = new ArrayList<>();

    public ThreadDemo(MyExcutor excutor) {
        this.excutor = excutor;

        if(excutor instanceof LockInterface){
            this.lock = (LockInterface) excutor;
        }
    }

    public void execute(int size) {
        execute(Params.builder().size(size).build());
    }

    public void execute(Params param) {
        lock = Optional.ofNullable(lock).orElse(new DefaultLock());
        ExecutorService service = Executors.newFixedThreadPool(300);

        for (int i = 0; i < param.getSize(); i++) {
            Future future = service.submit(() -> {
                try {
                    if (lock.getLock()) {
                        if (StringUtils.isNotEmpty(param.getType()) && param.getType().contains("2")) {
                            excutor.doExcuteRead(makeRequestParam());
                        } else {
                            excutor.doExcute(makeRequestParam());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.releaseLock();
                }
            });
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
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void futureCancel() throws InterruptedException {
        for (Future future : futureList) {
            try {
                future.cancel(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
