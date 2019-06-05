package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator;

// 肉夹馍 被修饰类
public class Roujiamo implements Pancake {
    @Override
    public void price() {
        System.out.println("Roujiamo price");
    }
}