package com.kdniao.logisticsfront.common.biz.service.impl.spring;

import com.kdniao.logisticsfront.common.biz.service.impl.spring.injection.InjectUserDao;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.injection.InjectUserDao2;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.injection.InjectUserDao3;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.injection.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectionTest {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    @Test
    public void test() {
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
