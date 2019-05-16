package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.builder.design.map;

// 指挥者
public class Director {
    private Builder builder;

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void build(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();

        builder.getResult();
    }

}
