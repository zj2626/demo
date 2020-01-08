package com.demo.common.service.spring.learn2.aop;

import com.demo.common.service.spring.learn2.aop.bean.DemoService;
import com.demo.common.service.spring.learn2.aop.bean.DemoServiceImpl;
import com.demo.common.service.spring.learn2.aop.configuration.SpringConfig;
import com.demo.common.service.spring.learn2.aop.configuration.SpringConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;
import java.util.UUID;

public class Demo {
    @Test
    public void testDemo() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("-------start---------");
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        service.queryById(UUID.randomUUID().toString(), "zj2626", "", "", 21, "", "", 0D);
        System.out.println("------------代理--------------");

        // 默认情况下context.getBean(DemoServiceImpl.class)会报错因为默认使用JDK动态代理,
        // 修改为@EnableAspectJAutoProxy(proxyTargetClass = true),则开启cglib代理, 就不会报错了
        DemoService service2 = null;
        try {
            service2 = context.getBean(DemoServiceImpl.class);
            System.out.println(">>> Spring容器中的对象:");
            System.out.println("SERVICE          :" + service2);
            System.out.println("SERVICE  SAME    :" + (service == service2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != service) {
                System.out.println("SERVICE  instanceof    :" + (service instanceof DemoService));
                System.out.println("SERVICE  instanceof    :" + (service instanceof DemoServiceImpl)); //使用JDK动态代理则为false
                System.out.println("SERVICE  instanceof    :" + (service instanceof Proxy)); //使用cglib动态代理则为false
            }
            if (null != service2) { // 使用JDK动态代理则为空
                System.out.println("SERVICE  instanceof    :" + (service2 instanceof DemoService));
                System.out.println("SERVICE  instanceof    :" + (service2 instanceof DemoServiceImpl));
                System.out.println("SERVICE  instanceof    :" + (service2 instanceof Proxy)); //使用cglib代理则为false
            }
        }
        System.out.println("-------end---------");
    }

    @Test
    public void testDemo2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig2.class);
        System.out.println("-------start---------\n");
        DemoService service = context.getBean(DemoService.class);
        DemoService service2 = context.getBean(DemoService.class);
        service.queryById(UUID.randomUUID().toString(), "zj2626", "", "", 21, "", "", 0D);
        System.out.println("-------____---------");
        service2.queryById(UUID.randomUUID().toString(), "zj2626", "", "", 21, "", "", 0D);
        System.out.println("-------end---------");
    }
}
