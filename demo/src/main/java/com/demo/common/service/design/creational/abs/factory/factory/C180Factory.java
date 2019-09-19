package com.demo.common.service.design.creational.abs.factory.factory;

import com.demo.common.service.design.creational.abs.factory.product.BenzC180;
import com.demo.common.service.design.creational.abs.factory.product.C180Navigator;
import com.demo.common.service.design.creational.abs.factory.product.Car;
import com.demo.common.service.design.creational.abs.factory.product.Navigator;

public class C180Factory implements CarFactory {
    /*
     * 产品族
     * */
    @Override
    public Car createCar() {
        return new BenzC180();
    }

    @Override
    public Navigator createNacigator() {
        return new C180Navigator();
    }
}
