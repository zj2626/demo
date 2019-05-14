package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product;

public class C180Navigator implements Navigator{
    public C180Navigator() {
        navigatorColor();
        navigatorPrice();
    }

    @Override
    public void navigatorColor() {
        System.out.println(this.getClass().getSimpleName() + " >> navigatorColor");
    }

    @Override
    public void navigatorPrice() {
        System.out.println(this.getClass().getSimpleName() + " >> navigatorPrice");
    }
}
