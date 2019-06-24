package com.kdniao.logisticsfront.common.biz.service.impl.spring.demo;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTest {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    @Test
    public void test1() {
        applicationContext.start();
    }

    @Test
    public void testL() {
        UserDao userDao = applicationContext.getBean(UserDao.class);
        System.out.println(userDao);
        System.out.println("###############");

        InjectUserDao injectUserDao = applicationContext.getBean(InjectUserDao.class);
        System.out.println(injectUserDao);
        System.out.println(injectUserDao.getU());
        System.out.println("###############");

        InjectUserDao2 injectUserDao2 = applicationContext.getBean(InjectUserDao2.class);
        System.out.println(injectUserDao2);
        System.out.println(injectUserDao2.getU());
        System.out.println("###############");

        InjectUserDao3 injectUserDao3 = applicationContext.getBean(InjectUserDao3.class);
        System.out.println(injectUserDao3);
        System.out.println(injectUserDao3.getU());
        System.out.println("###############");
    }
}
