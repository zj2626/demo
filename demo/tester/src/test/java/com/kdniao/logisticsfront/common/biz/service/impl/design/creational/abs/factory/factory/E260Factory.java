package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory;

import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.BenzE260;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Car;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.E260Navigator;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.product.Navigator;

public class E260Factory implements CarFactory {
    /*
     * 产品族
     * */
    @Override
    public Car createCar() {
        return new BenzE260();
    }

    @Override
    public Navigator createNacigator() {
        return new E260Navigator();
    }
}
