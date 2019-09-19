package com.demo.common.service.design.creational.simple.factory;

/**
 * 功能: 根据传入的不同字符串生产不同的产品
 */
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
