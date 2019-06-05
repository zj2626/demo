package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.simple.factory;

public class ConcreteProductA implements Product {
    private String name;

    @Override
    public String getName() {
        return ConcreteProductA.class.getName();
    }
}
