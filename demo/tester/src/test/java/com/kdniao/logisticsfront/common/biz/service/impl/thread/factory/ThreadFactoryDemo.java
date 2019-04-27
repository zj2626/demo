package com.kdniao.logisticsfront.common.biz.service.impl.thread.factory;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadFactoryDemo {

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        System.out.println(applicationContext);

        fuck();
    }


    private static void fuck(){
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());


        System.out.println(executorService);
    }
}
