package com.demo.common.service.design.structural.flyweight.design.map;

public class Client {

    public static void main(String[] args) {
        Flyweight flyweight = FlyweightFactory.getFlyweight("aaa");
        flyweight.operation(new UnsharedConcreteFlyweight("a"));
    }

}
