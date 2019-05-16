package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator;

import org.junit.Test;

/**
 * 3. 装饰模式(Decorator Pattern)
 * <p>
 * 装饰模式(Decorator Pattern) ：动态地给一个对象增加一些额外的职责(Responsibility)，就增加对象功能来说，装饰模式比生成子类实现更为灵活。
 * 其别名也可以称为包装器(Wrapper)，与适配器模式的别名相同，但它们适用于不同的场合。根据翻译的不同，装饰模式也有人称之为“油漆工模式”，它是一种对象结构型模式
 * <p>
 * 举例: JDBC驱动器
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/14 17:00 zhangj Exp $
 */
public class StructDemoTest {

    @Test
    public void test1() {
        Pancake pancake = new TornCake();
        pancake.price();

        pancake = new Roujiamo();
        pancake.price();

        pancake = new Ham(new Roujiamo());
        pancake.price();

        pancake = new FiredEgg(new TornCake());
        pancake.price();

        pancake = new Ham(new FiredEgg(new TornCake()));
        pancake.price();

        pancake = new Ham(new FiredEgg(new FiredEgg(new TornCake())));
        pancake.price();
    }

}
/**
 * 3.8. 优点
 * 装饰模式的优点:
 * <p>
 * 装饰模式与继承关系的目的都是要扩展对象的功能，但是装饰模式可以提供比继承更多的灵活性。
 * 可以通过一种动态的方式来扩展一个对象的功能，通过配置文件可以在运行时选择不同的装饰器，从而实现不同的行为。
 * 通过使用不同的具体装饰类以及这些装饰类的排列组合，可以创造出很多不同行为的组合。可以使用多个具体装饰类来装饰同一对象，得到功能更为强大的对象。
 * 具体构件类与具体装饰类可以独立变化，用户可以根据需要增加新的具体构件类和具体装饰类，在使用时再对其进行组合，原有代码无须改变，符合“开闭原则”
 * 3.9. 缺点
 * 装饰模式的缺点:
 * <p>
 * 使用装饰模式进行系统设计时将产生很多小对象，这些对象的区别在于它们之间相互连接的方式有所不同，而不是它们的类或者属性值有所不同，同时还将产生很多具体装饰类。这些装饰类和小对象的产生将增加系统的复杂度，加大学习与理解的难度。
 * 这种比继承更加灵活机动的特性，也同时意味着装饰模式比继承更加易于出错，排错也很困难，对于多次装饰的对象，调试时寻找错误可能需要逐级排查，较为烦琐。
 */
