package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.design.map;

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteDecorator(new ConcreteComponent());

        component.operation();
    }
}
