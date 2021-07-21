package com.demo.common.service.spring.start;

import com.demo.common.service.spring.start.bean.MyBeanService;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/*
 * bean属性定义[BeanDefinition]
 * *.id
 * *.name
 * *.scope
 * *.className
 * *.parent
 * *.lazyInit
 * *.Depends
 * *.Property
 *
 * bean存储定义[BeanDefinition]
 * *.XML
 * *.properties
 *
 * bean加载/解析[BeanDefinitionReader]
 * *.加载XML文件流
 * *.转化为Document
 * *.Document Element解析/注册为BeanDefinition[DocumentLoader/BeanDefinitionRegistry]
 * */
public class BeanDefinitionTest {

    /**
     * 自动生成bean bean的定义
     */
    @Test
    public void beanDefined() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(factory);
        beanDefinitionReader.loadBeanDefinitions("classpath:framework/spring.xml");

        MyBeanService fDao = (MyBeanService) factory.getBean("myBeanService");
        System.out.println(fDao);

    }
}
