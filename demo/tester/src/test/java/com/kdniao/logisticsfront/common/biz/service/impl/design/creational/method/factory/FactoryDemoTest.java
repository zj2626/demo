package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.method.factory;

import org.junit.Test;

/**
 * 2. 工厂方法模式(Factory Method Pattern)
 * <p>
 * 工厂方法模式(Factory Method Pattern)又称为工厂模式，也叫虚拟构造器(Virtual Constructor)模式或者多态工厂(Polymorphic Factory)模式，它属于类创建型模式。
 * 在工厂方法模式中，工厂父类负责定义创建产品对象的公共接口，
 * 而工厂子类则负责生成具体的产品对象，这样做的目的是将产品类的实例化操作延迟到工厂子类中完成，
 * 即通过工厂子类来确定究竟应该实例化哪一个具体产品类
 *
 * 举例: JDBC中的工厂方法(通过反射获得Connection)
 *
 * @author zhangj
 * @version $Id: FactoryDemoTest.java, v 0.1 2019/5/13 17:21 zhangj Exp $
 */
public class FactoryDemoTest {

    @Test
    public void test1() {
        Factory factory = new ConcreteFactoryA();
        Product product = factory.createProduct();
        System.out.println(product.getName());

        Factory factory2 = new ConcreteFactoryB();
        Product product2 = factory2.createProduct();
        System.out.println(product2.getName());
    }
}
/**
 * 2.8. 工厂方法模式的优点
 * 在工厂方法模式中，工厂方法用来创建客户所需要的产品，同时还向客户隐藏了哪种具体产品类将被实例化这一细节，用户只需要关心所需产品对应的工厂，无须关心创建细节，甚至无须知道具体产品类的类名。
 * 基于工厂角色和产品角色的多态性设计是工厂方法模式的关键。它能够使工厂可以自主确定创建何种产品对象，而如何创建这个对象的细节则完全封装在具体工厂内部。工厂方法模式之所以又被称为多态工厂模式，是因为所有的具体工厂类都具有同一抽象父类。
 * 使用工厂方法模式的另一个优点是在系统中加入新产品时，无须修改抽象工厂和抽象产品提供的接口，无须修改客户端，也无须修改其他的具体工厂和具体产品，而只要添加一个具体工厂和具体产品就可以了。这样，系统的可扩展性也就变得非常好，完全符合“开闭原则”。
 * 2.9. 工厂方法模式的缺点
 * 在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，系统中类的个数将成对增加，在一定程度上增加了系统的复杂度，有更多的类需要编译和运行，会给系统带来一些额外的开销。
 * 由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，增加了系统的抽象性和理解难度，且在实现时可能需要用到DOM、反射等技术，增加了系统的实现难度。
 */