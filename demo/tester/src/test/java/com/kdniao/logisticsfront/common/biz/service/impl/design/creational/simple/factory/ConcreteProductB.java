package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.simple.factory;

public class ConcreteProductB implements Product {
    private String name;

    @Override
    public String getName() {
        return ConcreteProductB.class.getName();
    }
}
