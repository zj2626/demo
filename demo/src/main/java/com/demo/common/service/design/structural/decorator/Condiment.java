package com.demo.common.service.design.structural.decorator;

// 配菜 修饰类父类
public class Condiment implements Pancake {
    private Pancake pancake;

    public Condiment(Pancake pancake) {
        this.pancake = pancake;
    }

    @Override
    public void price() {
        pancake.price();
    }
}
