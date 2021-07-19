package com.demo.common.service.spring.learn4.factoryBean;

import com.demo.common.service.spring.learn4.factoryBean.bean.DemoFactoryBeanService;
import com.demo.common.service.spring.learn4.factoryBean.configuration.SpringConfig;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    @Test
    public void testConfiguration() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        System.out.println("_______start__________\n");

        /**
         * 通过 FactoryBean 注入的实例, 使用的beanName为 FactoryBean 定义的名称, 而 FactoryBean 的实例名称为 前者加前缀"&"即可
         */
        DemoFactoryBeanService factoryBeanService = context.getBean(DemoFactoryBeanService.class);
        System.out.println("SERVICE          :" + factoryBeanService);
        factoryBeanService.getMsg("WTF1");
        System.out.println();

        // DemoFactoryBeanService 实例定义的beanName为 demoTestFactoryBean
        DemoFactoryBeanService factoryBeanService2 = (DemoFactoryBeanService) context.getBean("demoTestFactoryBean");
        System.out.println("SERVICE          :" + factoryBeanService2);
        factoryBeanService2.getMsg("WTF2");
        System.out.println();

        // MyFactoryBean 实例定义的beanName为 &demoTestFactoryBean
        FactoryBean myFactoryBean = (FactoryBean) context.getBean("&demoTestFactoryBean");
        System.out.println("SERVICE          :" + myFactoryBean.getObject());
        System.out.println("SERVICE  FactoryBean :" + myFactoryBean);
    }
}
