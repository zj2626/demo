package com.kdniao.logisticsfront.common.biz.service.impl.mybatis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(applicationContext);
    }
}
