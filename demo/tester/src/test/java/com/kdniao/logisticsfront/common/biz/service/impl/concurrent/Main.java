package com.kdniao.logisticsfront.common.biz.service.impl.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");
        blockingQueue.add("D");
        blockingQueue.add("FFF");

        try {
            System.out.println("_____________1");
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("_____________2");
            System.out.println(blockingQueue.poll(1000, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("_____________3");
            System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("_____________4");
            System.out.println(blockingQueue.poll(1000, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setName("");
        user.setAge(0);
    }
}
