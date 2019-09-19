package com.demo.common.service.hystrix;

public class MyExecutor implements Runnable {
    private String name;
    private String groupName;
    private long time;

    public MyExecutor(String name, String groupName, long time) {
        this.name = name;
        this.groupName = groupName;
        this.time = time;
    }


    @Override
    public void run() {
        System.out.println("Begin > " + Thread.currentThread().getName());

        HystrixRequest hystrixRequest = new HystrixRequest(name, groupName, time);
        String result = hystrixRequest.execute();
        System.out.println("Result > " + Thread.currentThread().getName() + " [" + result + "]\n");
    }
}
