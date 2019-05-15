package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.bridge.design.map;

public class Client {
    public static void main(String[] args) {
        RefinedAbstraction refinedAbstraction = new RefinedAbstraction(new ConcreteImplementorA());
        refinedAbstraction.operation();
        refinedAbstraction.otherOperation();
    }
}
