package com.demo.common.service.spring.learn0.injection;

import com.demo.common.service.spring.learn0.injection.bean.DemoService;
import com.demo.common.service.spring.learn0.injection.configuration.SpringConfig;
import com.demo.common.service.spring.learn0.injection.configuration.SpringConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    /*
     1. IOC(控制反转)是面向对象编程的一种设计原则, 可以用来减少代码之间的耦合度, 其最常会用的实现方式叫做依赖注入(DI),还有种方式叫依赖查找(lookup)
     2. Spring三种编码风格
        1.xml => schemal-based
        2.注解 => annotation
        3.java Configuration => java-based
     3.依赖注入两种方式
        1.setter方法
        2.构造方法
        3.接口注入(spring4已取消)
     */

    /**
     * 基于xml的依赖注入+自动注入byName
     * -setter注入(Setter-based Dependency Injection)
     * -构造方法注入(Constructor-based Dependency Injection)
     */
    @Test
    public void testXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:framework/spring-injection-byName.xml");
        DemoService service = (DemoService) context.getBean("demoService01");
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        // 如果配置文件中不设置 "default-autowire", 则不会自动注入, 需要手动指定property或者constructor-arg
        System.out.println(">>> SERVICE依赖的DAO :");
        // setter注入即通过指定名称的setter方法注入对应的依赖(注入依赖名称为demoDao01,则方法名为setDemoDao01)
        service.getDaoInfo();
    }

    /**
     * 基于xml的依赖自动注入-byType
     */
    @Test
    public void testXml2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:framework/spring-injection-byType.xml");
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
    }

    /**
     * 基于注解的依赖自动注入
     */
    @Test
    public void testAnontation() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:framework/spring-injection-annotation.xml");
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
    }

    /**
     * 基于java-based+注解的依赖自动注入
     */
    @Test
    public void testConfiguration() {
        // 具备解析开启注解的功能
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
    }

    /**
     * 基于java-based+注解+xml的依赖自动注入
     */
    @Test
    public void testConfiguration2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig2.class);
        DemoService service = context.getBean(DemoService.class);
        System.out.println(">>> Spring容器中的对象:");
        System.out.println("SERVICE          :" + service);
        System.out.println(">>> SERVICE依赖的DAO :");
        service.getDaoInfo();
    }
}
