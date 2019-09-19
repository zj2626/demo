package com.demo.common.service.design.creational.simple.factory;

public class ConcreteProductA implements Product {
    private String name;

    @Override
    public String getName() {
        return ConcreteProductA.class.getName();
    }
}
