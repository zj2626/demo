package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.method.factory;

public class ConcreteFactoryB implements Factory {

    public Product createProduct() {
        return new ConcreteProductB();
    }
}
