package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.builder.design.map;

public class Client {
    public static void main(String[] args) {
        Director director = new Director();
        director.setBuilder(new ConcreteBuilder());

        director.build();
    }
}
