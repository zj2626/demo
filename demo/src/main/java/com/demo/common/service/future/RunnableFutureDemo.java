package com.demo.common.service.future;

import java.util.concurrent.*;

public class RunnableFutureDemo<String> implements RunnableFuture<String> {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("RunnableFuture " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public String get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
