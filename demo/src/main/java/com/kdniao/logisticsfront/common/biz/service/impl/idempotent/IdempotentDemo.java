package com.kdniao.logisticsfront.common.biz.service.impl.idempotent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IdempotentDemo {
    private static Integer value = 0;
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    public static void main(String[] args) {
//        applicationContext.getBean("");

        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    int temp = value;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    value = temp + 1;
                }
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();


        System.out.println(value);
    }
}
