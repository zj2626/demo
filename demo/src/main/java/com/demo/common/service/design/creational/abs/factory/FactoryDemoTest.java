package com.demo.common.service.design.creational.abs.factory;

import com.demo.common.service.design.creational.abs.factory.factory.C180Factory;
import com.demo.common.service.design.creational.abs.factory.factory.CarFactory;
import com.demo.common.service.design.creational.abs.factory.factory.E260Factory;
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
 * 3.8. 优点
 * 抽象工厂模式隔离了具体类的生成，使得客户并不需要知道什么被创建。由于这种隔离，更换一个具体工厂就变得相对容易。所有的具体工厂都实现了抽象工厂中定义的那些公共接口，因此只需改变具体工厂的实例，就可以在某种程度上改变整个软件系统的行为。另外，应用抽象工厂模式可以实现高内聚低耦合的设计目的，因此抽象工厂模式得到了广泛的应用。
 * 当一个产品族中的多个对象被设计成一起工作时，它能够保证客户端始终只使用同一个产品族中的对象。这对一些需要根据当前环境来决定其行为的软件系统来说，是一种非常实用的设计模式。
 * 增加新的具体工厂和产品族很方便，无须修改已有系统，符合“开闭原则”。
 * 3.9. 缺点
 * 在添加新的产品对象时，难以扩展抽象工厂来生产新种类的产品，这是因为在抽象工厂角色中规定了所有可能被创建的产品集合，要支持新种类的产品就意味着要对该接口进行扩展，而这将涉及到对抽象工厂角色及其所有子类的修改，显然会带来较大的不便。
 * 开闭原则的倾斜性（增加新的工厂和产品族容易，增加新的产品等级结构麻烦）。
 */