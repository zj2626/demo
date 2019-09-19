package com.demo.common.service.design.creational.builder;

/**
 * Director：指挥者
 * <p>
 * 功能: 根据选择(实例化)不同的工厂类生产不同的产品,并且生产的具体行为在Builder中但是生产整体控制在Director这里
 */
public class Director {
    public Product createProduct(Builder builder) {
        builder.buildBasic();
        builder.buildWalls();
        builder.roofed();
        return builder.buildProduct();
    }
}
