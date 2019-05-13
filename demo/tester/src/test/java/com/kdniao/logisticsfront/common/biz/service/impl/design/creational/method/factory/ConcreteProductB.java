package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.method.factory;

public class ConcreteProductB implements Product {
    private String name;

    @Override
    public String getName() {
        return ConcreteProductB.class.getName();
    }
}
