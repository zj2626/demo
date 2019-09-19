package com.demo.common.service.design.structural.decorator.design.map;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteDecorator(new ConcreteComponent());

        component.operation();
    }
}
