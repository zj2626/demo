package com.demo.common.service.spring.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAwareDemo {
    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>");

        MyBeanNameAware myBeanNameAware = context.getBean(MyBeanNameAware.class);
        myBeanNameAware.doDemo();
    }

}
