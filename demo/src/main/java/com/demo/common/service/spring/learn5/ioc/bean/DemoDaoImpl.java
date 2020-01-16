package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoDaoImpl implements DemoDao {
    private Integer id;

    @Override
    public void doQuery() {
        System.out.println("OHHHHHHHHH " + id);
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
