package com.demo.common.service.design.structural.bridge.design.map;

public class Client {
    public static void main(String[] args) {
        Abstraction refinedAbstraction = new RefinedAbstraction(new ConcreteImplementorA());
        refinedAbstraction.operation();
        refinedAbstraction.otherOperation();
    }
}
