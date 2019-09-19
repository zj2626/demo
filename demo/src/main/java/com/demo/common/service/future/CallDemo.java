package com.demo.common.service.future;

import java.util.concurrent.Callable;

public class CallDemo<String> implements Callable<String> {

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
