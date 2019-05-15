package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory;

import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Car;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Navigator;

/**
 * 功能: 根据传入的不同字符串生产不同的产品族, 每个工厂类中生产的产品属于同一个产品族
 */
public interface CarFactory {
    /**
     * 创建车的方法
     */
    public Car createCar();

    /**
     * 创建导航仪的方法
     */
    public Navigator createNacigator();
}
