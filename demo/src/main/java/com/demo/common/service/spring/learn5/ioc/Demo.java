package com.demo.common.service.spring.learn5.ioc;

import com.demo.common.service.spring.learn5.ioc.bean.DemoBeanService;
import com.demo.common.service.spring.learn5.ioc.configuration.MyBeanFactory;
import org.junit.Test;

public class Demo {
    /*
    模仿Spring 进行依赖注入
     */
    @Test
    public void test() throws Exception {
        MyBeanFactory beanFactory = new MyBeanFactory("spring-learn5-ioc.xml");
        DemoBeanService beanService = (DemoBeanService) beanFactory.getBean("demoBeanService");
        System.out.println(beanService.getMsg("EEEEEEEEEEEEE"));
    }
}
