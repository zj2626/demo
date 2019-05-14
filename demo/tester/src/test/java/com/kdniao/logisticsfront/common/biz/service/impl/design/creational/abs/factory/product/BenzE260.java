package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product;

public class BenzE260 implements Car{
    public BenzE260() {
        color();
        speed();
        price();
    }

    @Override
    public void color() {
        System.out.println(this.getClass().getSimpleName() + " >> color");
    }

    @Override
    public void speed() {
        System.out.println(this.getClass().getSimpleName() + " >> speed");
    }

    @Override
    public void price() {
        System.out.println(this.getClass().getSimpleName() + " >> price");
    }
}
