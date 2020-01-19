package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoDeleteBeanDaoImpl implements DemoDeleteBeanDao {
    @Override
    public void doDelete() {
        System.out.println("delete sql");
    }
}
