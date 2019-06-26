package com.kdniao.logisticsfront.common.biz.service.impl.spring.demo;

import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.DemoSon;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.MyBeanDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTest {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        applicationContext.start();
    }

    @Test
    public void testL() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

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

    @Test
    public void testL2() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:framework/demo.xml");

        DemoSon demoSon = applicationContext.getBean(DemoSon.class);
        System.out.println(demoSon);
    }
}
