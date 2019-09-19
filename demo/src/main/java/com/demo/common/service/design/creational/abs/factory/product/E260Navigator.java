package com.demo.common.service.design.creational.abs.factory.product;

public class E260Navigator implements Navigator {
    public E260Navigator() {
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
