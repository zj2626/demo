package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoDaoImpl implements DemoDao {
    private Integer id;

    @Override
    public void doQuery() {
        System.out.println("DemoDaoImpl doQuery " + id);
    }
}
