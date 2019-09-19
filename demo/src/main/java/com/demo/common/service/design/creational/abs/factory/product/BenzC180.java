package com.demo.common.service.design.creational.abs.factory.product;

public class BenzC180 implements Car{

    public BenzC180() {
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
