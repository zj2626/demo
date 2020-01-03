package com.demo.common.service.spring.learn1.lifecycle;

import com.demo.common.service.spring.learn1.lifecycle.bean.DemoLifecycleService;
import com.demo.common.service.spring.learn1.lifecycle.configuration.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    @Test
    public void testDemo() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DemoLifecycleService service = context.getBean(DemoLifecycleService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
    }
}
