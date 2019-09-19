package com.demo.common.service.design.structural.decorator;

public class FiredEgg extends Condiment {
    public FiredEgg(Pancake pancake) {
        super(pancake);
    }

    @Override
    public void price() {
        System.out.print("加鸡蛋 ");
        super.price();
    }
}
