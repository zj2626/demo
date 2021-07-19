package com.demo.common.service.spring.learn4.factoryBean.bean;

public class DemoFactoryBeanServiceImpl implements DemoFactoryBeanService {
    @Override
    public String getMsg(String msg) {
        System.out.println("FactoryBean getMsg " + msg);
        return "FactoryBean";
    }
}
