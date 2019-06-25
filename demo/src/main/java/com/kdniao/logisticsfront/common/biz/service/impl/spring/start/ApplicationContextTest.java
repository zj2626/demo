package com.kdniao.logisticsfront.common.biz.service.impl.spring.start;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContext 构造方法:
 * 1.加载资源XML
 * 2.创建BeanFactory, 解析XML文件到BeanDifinition, 注册到BeanFactory中
 * 3.初始化BeanFactory,对Bean(Singleton且非LazyInit非抽象的Bean)进行创建
 */
public class ApplicationContextTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:framework/spring.xml");

//        applicationContext.start();
//        MyBeanDao myBeanDao = (MyBeanDao) applicationContext.getBean("myBeanDao");
    }
}
