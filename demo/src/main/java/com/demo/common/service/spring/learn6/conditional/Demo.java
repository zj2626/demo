package com.demo.common.service.spring.learn6.conditional;

import com.demo.common.service.spring.learn6.conditional.bean.DemoDao;
import com.demo.common.service.spring.learn6.conditional.configuration.SpringConfig;
import com.demo.common.service.spring.learn6.conditional.configuration.SpringConfig2;
import com.demo.common.service.spring.learn6.conditional.configuration.SpringConfig3;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    @Test
    public void test() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DemoDao defaultDao = (DemoDao) context.getBean("DemoDao");
        System.out.println(defaultDao);
        System.out.println(defaultDao.getId());
        System.out.println("-------start---------");
        DemoDao getDao = context.getBean(DemoDao.class);
        System.out.println(getDao);
    }

    @Test
    public void test2() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig2.class);
        System.out.println("-------start---------");
        System.out.println(context.getBean(DemoDao.class));
        DemoDao getDao = (DemoDao) context.getBean("demoDao");
        System.out.println(getDao);
    }

    @Test
    public void test3() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig3.class);
        System.out.println(context.getBean(DemoDao.class));
        System.out.println("-------start---------");
        DemoDao getDao = (DemoDao) context.getBean("demoDao");
        System.out.println(getDao);
    }
}
