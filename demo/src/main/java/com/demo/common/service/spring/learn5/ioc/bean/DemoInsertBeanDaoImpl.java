package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoInsertBeanDaoImpl implements DemoInsertBeanDao {
    @Override
    public void doInsert() {
        System.out.println("DemoInsertBeanDaoImpl doInsert");
    }
}
