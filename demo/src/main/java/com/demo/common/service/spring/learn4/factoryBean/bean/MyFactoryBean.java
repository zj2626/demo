package com.demo.common.service.spring.learn4.factoryBean.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("demoTestFactoryBean")
public class MyFactoryBean implements FactoryBean<DemoFactoryBeanService> {
    private DemoFactoryBeanService obj;

    @Override
    public DemoFactoryBeanService getObject() throws Exception {
        if (null == obj) {
            obj = new DemoFactoryBeanServiceImpl();
        }
        return obj;
    }

    @Override
    public Class<?> getObjectType() {
        return DemoFactoryBeanServiceImpl.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
