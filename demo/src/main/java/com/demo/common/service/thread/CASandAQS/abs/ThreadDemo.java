package com.demo.common.service.thread.CASandAQS.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadDemo {
    private static Excutor excutor;
    private static List<Future> futureList = new ArrayList<>();
    
    public ThreadDemo(Excutor excutor) {
        ThreadDemo.excutor = excutor;
    }
    
    public void execute(int size) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int i = 0; i < size; i++) {
            Future future = service.submit(() -> {
                try {
                    excutor.doExcute(makeRequestParam());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            futureList.add(future);
        }
    }
    
    private Map<String, String> makeRequestParam() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("id", "m32nvpfaagcmf");
        parameter.put("kitchenId", "metu8341dq0a5");
        parameter.put("name", "品类一001");
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
                future.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
