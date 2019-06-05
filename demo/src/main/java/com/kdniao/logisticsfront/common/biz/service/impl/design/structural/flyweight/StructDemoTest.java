package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight;

import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map.ConcreteFlyweight;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map.Flyweight;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map.FlyweightFactory;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map.UnsharedConcreteFlyweight;
import org.junit.Test;

/**
 * 5. 享元模式(Flyweight Pattern)
 * <p>
 * 享元模式(Flyweight Pattern)：运用共享技术有效地支持大量细粒度对象的复用。StructDemoTest
 * 系统只使用少量的对象，而这些对象都很相似，状态变化很小，可以实现对象的多次复用。
 * 由于享元模式要求能够共享的对象必须是细粒度对象，因此它又称为轻量级模式，它是一种对象结构型模式。
 * <p>
 * 举例:
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/14 17:00 zhangj Exp $
 */
public class StructDemoTest {

    @Test
    public void test1() {
        Flyweight flyweight = FlyweightFactory.getFlyweight("aaa");
        Flyweight flyweight2 = FlyweightFactory.getFlyweight("bbb");
        Flyweight flyweight3 = FlyweightFactory.getFlyweight("aaa");

        System.out.println(flyweight.hashCode());
        System.out.println(flyweight2.hashCode());
        System.out.println(flyweight3.hashCode());

        // 内部状态                 外部状态
        flyweight3.operation(new UnsharedConcreteFlyweight("a"));
        flyweight3.operation(new UnsharedConcreteFlyweight("b"));
    }

}
/**
 * 5.8. 优点
 * 享元模式的优点
 * <p>
 * 享元模式的优点在于它可以极大减少内存中对象的数量，使得相同对象或相似对象在内存中只保存一份。
 * 享元模式的外部状态相对独立，而且不会影响其内部状态，从而使得享元对象可以在不同的环境中被共享。
 * 5.9. 缺点
 * 享元模式的缺点
 * <p>
 * 享元模式使得系统更加复杂，需要分离出内部状态和外部状态，这使得程序的逻辑复杂化。
 * 为了使对象可以共享，享元模式需要将享元对象的状态外部化，而读取外部状态使得运行时间变长。
 */
