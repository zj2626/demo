package com.demo.common.service.design.creational.method.factory;

public class ConcreteProductB implements Product {
    private String name;

    @Override
    public String getName() {
        return ConcreteProductB.class.getName();
    }
}
