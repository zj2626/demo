package com.demo.common.service.thread.abs;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {
    private static MyExcutor excutor;
    private static List<Future> futureList = new ArrayList<>();

    public ThreadDemo(MyExcutor excutor) {
        ThreadDemo.excutor = excutor;
    }

    public void execute(int size) {
        execute(Params.builder().size(size).build());
    }

    public void execute(Params param) {
        ExecutorService service = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < param.getSize(); i++) {
            Future future = service.submit(() -> {
                try {
                    if (StringUtils.isNotEmpty(param.getType()) && param.getType().contains("2")) {
                        excutor.doExcuteRead(makeRequestParam());
                    } else {
                        excutor.doExcute(makeRequestParam());
                    }
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
