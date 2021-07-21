package com.demo.common.service.thread.future.impl;

import java.util.concurrent.Callable;

public class CallableDemo<String> implements Callable<String> {

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 4; i++) {
            System.out.println("CallDemo " + i);
            Thread.sleep(1000);
        }

        System.out.println("call done");
        return (String) "fffffffff";
    }
}
