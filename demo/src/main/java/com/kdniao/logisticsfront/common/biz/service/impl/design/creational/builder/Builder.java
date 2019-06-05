package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.builder;

/**
 * Builder：抽象建造者
 */
public interface Builder {
    public void buildBasic();

    public void buildWalls();

    public void roofed();

    public Product buildProduct();
}
