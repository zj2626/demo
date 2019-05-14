package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory;

import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Car;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Navigator;

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
