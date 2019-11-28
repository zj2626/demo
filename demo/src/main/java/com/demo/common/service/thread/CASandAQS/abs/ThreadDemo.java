package com.demo.common.service.thread.CASandAQS.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {
    private static Excutor excutor;
    
    public ThreadDemo(Excutor excutor) {
        ThreadDemo.excutor = excutor;
    }
    
    public void execute() throws InterruptedException {
        List<Future> futureList = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Future future = service.submit(() -> {
                try {
                    Thread.sleep(200);
                    excutor.doExcute(makeRequestParam());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
        futureGet(futureList);
    }
    
    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "品类一001");
        parameter.put("skuStatus", "1");
        return parameter;
    }
    
    private void futureGet(List<Future> futureList) throws InterruptedException {
        for (Future future : futureList) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
