package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.demo1;

/**
 * 将Robot转换成为RealDog (RealDog是Adaptee适配者类, Robot是target目标类)
 * <p>
 * 类适配器模式
 */
public class Adapter extends RealDog implements Robot {
    @Override
    public void cry() {
        System.out.print("机器人模仿：");
        super.shout();
    }

    @Override
    public void move() {
        System.out.print("机器人模仿：");
        super.run();
    }
}
