package com.demo.common.service.hystrix;

import java.util.concurrent.Future;

public class MyExecutorAsync implements Runnable {
    private String name;
    private String groupName;
    private long time;

    public MyExecutorAsync(String name, String groupName, long time) {
        this.name = name;
        this.groupName = groupName;
        this.time = time;
    }


    @Override
    public void run() {
        String result = "";
        System.out.println("Begin > " + Thread.currentThread().getName() + " "  + groupName + " * " + name);

        try {
            HystrixRequest hystrixRequest = new HystrixRequest(name, groupName, time);
            Future<String> future = hystrixRequest.queue();
//            System.out.println("#######0");
//            for (int i = 1; i <= 3; i++) {
//                try {
//                    System.out.println("#######" + i);
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("#######等待线程执行完毕: " + groupName);
            result = future.get();
            System.out.println("Result > " + Thread.currentThread().getName() + " "  + groupName + " * " + name + " [" + result + "]\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
