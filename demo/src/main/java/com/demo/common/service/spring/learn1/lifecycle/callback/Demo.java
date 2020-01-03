package com.demo.common.service.spring.learn1.lifecycle.callback;

import com.demo.common.service.spring.learn1.lifecycle.callback.bean.DemoLifecycleService;
import com.demo.common.service.spring.learn1.lifecycle.callback.configuration.SpringConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    /**
     * 生命周期的回调
     */
    @Test
    public void testDemo() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("-------start---------");
        DemoLifecycleService service = context.getBean(DemoLifecycleService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
        System.out.println("-------end---------");
    }
}
