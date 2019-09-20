package com.demo.common.service.spring.aware;

import org.springframework.beans.factory.BeanNameAware;

public class MyBeanNameAware implements BeanNameAware {
    private String beanName = null;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("setBeanName()");
    }

    public void init(){
        System.out.println("正在执行初始化方法init");
    }

    public void doDemo(){
        System.out.println("doDemo: " + beanName);
    }
}
