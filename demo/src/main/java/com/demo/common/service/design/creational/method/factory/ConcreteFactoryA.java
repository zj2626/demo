package com.demo.common.service.design.creational.method.factory;

/**
 * 功能: 根据选择(实例化)不同的工厂类生产不同的产品
 */
public class ConcreteFactoryA implements Factory {

    public Product createProduct() {
        return new ConcreteProductA();
    }
}
