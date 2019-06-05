package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator;

// 手抓饼 被修饰类
public class TornCake implements Pancake {
    @Override
    public void price() {
        System.out.println("TornCake price");
    }
}
