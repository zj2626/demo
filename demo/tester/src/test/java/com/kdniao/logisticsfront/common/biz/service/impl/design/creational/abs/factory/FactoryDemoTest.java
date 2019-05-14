package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory;

import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory.C180Factory;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory.CarFactory;
import com.kdniao.logisticsfront.common.biz.service.impl.design.creational.abs.factory.factory.E260Factory;
import org.junit.Test;

/**
 * 3. 抽象工厂模式(Abstract Factory Pattern)
 * <p>
 * 抽象工厂模式(Abstract Factory Pattern)：提供一个创建一系列相关或相互依赖对象的接口，而无须指定它们具体的类。
 * 抽象工厂模式又称为Kit模式，属于对象创建型模式。
 *
 * @author zhangj
 * @version $Id: FactoryDemoTest.java, v 0.1 2019/5/13 17:21 zhangj Exp $
 */
public class FactoryDemoTest {

    @Test
    public void test1() {
        CarFactory carFactory2 = new E260Factory();
        carFactory2.createCar();
        carFactory2.createNacigator();

        CarFactory carFactory = new C180Factory();
        carFactory.createCar();
        carFactory.createNacigator();


    }
}
/**
 *
 */