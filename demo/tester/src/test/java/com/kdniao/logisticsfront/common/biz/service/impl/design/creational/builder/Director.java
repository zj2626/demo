package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.builder;

/**
 * Director：指挥者
 */
public class Director {
    public Product createProduct(Builder builder){
        builder.buildBasic();
        builder.buildWalls();
        builder.roofed();
        return builder.buildProduct();
    }
}
