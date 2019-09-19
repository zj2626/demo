package com.demo.common.service.design.structural.adapter.demo1;

/**
 * 将Robot转换成为RealDog (RealDog是Adaptee适配者类, Robot是target目标类)
 * <p>
 * 类适配器模式
 * <p>
 * 功能: 机器人拥有模仿发声和移动的能力, 要求其能根据不同的实体模仿不同的行为(这里需要模仿狗的叫和跑)
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
