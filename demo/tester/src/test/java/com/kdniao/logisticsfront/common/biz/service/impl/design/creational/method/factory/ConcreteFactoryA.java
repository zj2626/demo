package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.method.factory;

public class ConcreteFactoryA implements Factory {

    public Product createProduct() {
        return new ConcreteProductA();
    }
}
