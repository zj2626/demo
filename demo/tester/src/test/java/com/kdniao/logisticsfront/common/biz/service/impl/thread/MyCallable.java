package com.kdniao.logisticsfront.common.biz.service.impl.thread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    private long waitTime;

    public MyCallable(int timeInMillis) {
        this.waitTime = timeInMillis;
    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " -#######- " + i);
            Thread.sleep(waitTime);
        }
        return Thread.currentThread().getName();
    }
}
