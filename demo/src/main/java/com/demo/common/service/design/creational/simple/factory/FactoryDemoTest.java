package com.demo.common.service.design.creational.simple.factory;

import org.junit.Test;

/**
 * 1. 简单工厂模式(Simple Factory Pattern)
 * <p>
 * 简单工厂模式(Simple Factory Pattern)：又称为静态工厂方法(Static Factory Method)模式，
 * 它属于类创建型模式。
 * 在简单工厂模式中，可以根据参数的不同返回不同类的实例。
 * 简单工厂模式专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。
 *
 * 举例: java.text.DateFormat; 加密算法的密钥生成器
 *
 * @author zhangj
 * @version $Id: FactoryDemoTest.java, v 0.1 2019/5/13 17:21 zhangj Exp $
 */
public class FactoryDemoTest {

    @Test
    public void test1() {
        Product product = Factory.createProduct("a");
        System.out.println(product.getName());

        Product product2 = Factory.createProduct("B");
        System.out.println(product2.getName());
    }
}
/**
 * 1.8. 简单工厂模式的优点
 * 工厂类含有必要的判断逻辑，可以决定在什么时候创建哪一个产品类的实例，客户端可以免除直接创建产品对象的责任，而仅仅“消费”产品；简单工厂模式通过这种做法实现了对责任的分割，它提供了专门的工厂类用于创建对象。
 * 客户端无须知道所创建的具体产品类的类名，只需要知道具体产品类所对应的参数即可，对于一些复杂的类名，通过简单工厂模式可以减少使用者的记忆量。
 * 通过引入配置文件，可以在不修改任何客户端代码的情况下更换和增加新的具体产品类，在一定程度上提高了系统的灵活性。
 * 1.9. 简单工厂模式的缺点
 * 由于工厂类集中了所有产品创建逻辑，一旦不能正常工作，整个系统都要受到影响。
 * 使用简单工厂模式将会增加系统中类的个数，在一定程序上增加了系统的复杂度和理解难度。
 * 系统扩展困难，一旦添加新产品就不得不修改工厂逻辑，在产品类型较多时，有可能造成工厂逻辑过于复杂，不利于系统的扩展和维护。
 * 简单工厂模式由于使用了静态工厂方法，造成工厂角色无法形成基于继承的等级结构。
 */
