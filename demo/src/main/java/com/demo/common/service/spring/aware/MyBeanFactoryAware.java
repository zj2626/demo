package com.demo.common.service.spring.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class MyBeanFactoryAware implements BeanFactoryAware {


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("I belong to ："+beanFactory);
    }


    public void init(){
        System.out.println("正在执行初始化方法init factory");
    }
}
