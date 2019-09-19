package com.demo.common.service.design.structural.decorator;

public class Ham extends Condiment {
    public Ham(Pancake pancake) {
        super(pancake);
    }

    @Override
    public void price() {
        System.out.print("加火腿 ");
        super.price();
    }
}
