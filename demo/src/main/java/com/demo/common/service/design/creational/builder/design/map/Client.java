package com.demo.common.service.design.creational.builder.design.map;

public class Client {
    public static void main(String[] args) {
        Director director = new Director();
        director.setBuilder(new ConcreteBuilder());

        director.build();
    }
}
