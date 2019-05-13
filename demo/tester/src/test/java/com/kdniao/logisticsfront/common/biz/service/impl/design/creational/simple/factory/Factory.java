package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.simple.factory;

public class Factory {

    public static Product createProduct(String proName) {
        if ("A".equalsIgnoreCase(proName)) {
            return new ConcreteProductA();
        } else if ("B".equalsIgnoreCase(proName)) {
            return new ConcreteProductB();
        } else {
            return null;
        }
    }
}
