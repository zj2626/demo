package com.demo.common.service.spring.learn4.factoryBean.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("demoTestFactoryBean")
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new DemoFactoryBeanServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return DemoFactoryBeanServiceImpl.class;
    }
}
