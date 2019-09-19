package com.demo.common.service.design.creational.method.factory;

public class ConcreteFactoryB implements Factory {

    public Product createProduct() {
        return new ConcreteProductB();
    }
}
