package com.demo.common.service.design.creational.builder.design.map;

// 抽象建造者
public interface Builder {
    public void buildPartA();

    public void buildPartB();

    public void buildPartC();

    public void getResult();
}
