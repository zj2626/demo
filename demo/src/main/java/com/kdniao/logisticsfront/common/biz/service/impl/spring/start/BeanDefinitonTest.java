package com.kdniao.logisticsfront.common.biz.service.impl.spring.start;

import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.MyBeanDao;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/*
 * bean属性定义[BeanDefiniton]
 * *.id
 * *.name
 * *.scope
 * *.className
 * *.parent
 * *.lazyInit
 * *.Depends
 * *.Property
 *
 * bean存储定义[BeanDefiniton]
 * *.XML
 * *.properties
 *
 * bean加载/解析[BeanDefinitionReader]
 * *.加载XML文件流
 * *.转化为Document
 * *.Document Element解析/注册为BeanDefiniton[DocumentLoader/BeanDefinitionRegistry]
 * */
public class BeanDefinitonTest {

    /**
     * 自动生成bean bean的定义
     */
    @Test
    public void beanDefined() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(factory);
        beanDefinitionReader.loadBeanDefinitions("classpath:framework/spring.xml");

        MyBeanDao fDao = (MyBeanDao) factory.getBean("myBeanDao");
        System.out.println(fDao);

    }
}
