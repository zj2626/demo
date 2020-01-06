package com.demo.common.service.spring.learn2.aop;

import com.demo.common.service.spring.learn2.aop.bean.DemoService;
import com.demo.common.service.spring.learn2.aop.configuration.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    @Test
    public void testDemo() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("-------start---------");
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        service.queryById();
        System.out.println("-------end---------");
    }
}
