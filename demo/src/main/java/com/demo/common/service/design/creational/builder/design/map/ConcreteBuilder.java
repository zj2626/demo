package com.demo.common.service.design.creational.builder.design.map;

// 具体建造者
public class ConcreteBuilder implements Builder{
    @Override
    public void buildPartA() {
        System.out.println("buildPartA");
    }

    @Override
    public void buildPartB() {
        System.out.println("buildPartB");
    }

    @Override
    public void buildPartC() {
        System.out.println("buildPartC");
    }

    @Override
    public void getResult() {
        System.out.println("getResult");
    }
}
