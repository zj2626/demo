package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.prototype;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * 6. 原型模式(Prototype Pattern)
 * <p>
 * 原型模式（Prototype Pattern）是用于创建重复的对象，同时又能保证性能。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 这种模式是实现了一个原型接口，该接口用于创建当前对象的克隆。当直接创建对象的代价比较大时，则采用这种模式。
 * 例如，一个对象需要在一个高代价的数据库操作之后被创建。我们可以缓存该对象，在下一个请求时返回它的克隆，在需要的时候更新数据库，以此来减少数据库调用。
 *
 * @author zhangj
 * @version $Id: DemoTest.java, v 0.1 2019/7/17 17:21 zhangj Exp $
 */
public class DemoTest {

    /*浅复制： 复制前后对象复制，地址不同；引用数据类型属性是引用传递，地址相同*/
    @Test
    public void test1() throws Exception {
        Prototype p = new Prototype();
        p.setName("AbcE");
        p.setList(Lists.newArrayList("fffff", "fffff", "ffabcf"));
        p.setAge(10);
        p.setSet(Lists.newArrayList("fffff", "fffff", "ffabcf"));
        p.setHome("home");
        p.setProp(new Prop());
        System.out.println(p + "___" + p.hashCode());
        System.out.println(p.getProp().hashCode());
        System.out.println(p.getList().hashCode());
        System.out.println();

        Prototype p2 = (Prototype) p.clone();
        System.out.println(p2 + "___" + p2.hashCode());
        System.out.println(p2.getProp().hashCode());
        System.out.println(p2.getList().hashCode());

//        PrototypeAfter p3 = (PrototypeAfter) p.clone();
//        System.out.println(p3);
    }

    /*深复制*/

}

/*
 *
 * 优点： 1、性能提高。 2、逃避构造函数的约束。
 * 缺点： 1、配备克隆方法需要对类的功能进行通盘考虑，这对于全新的类不是很难，但对于已有的类不一定很容易，特别当一个类引用不支持串行化的间接对象，或者引用含有循环结构的时候。 2、必须实现 Cloneable 接口。
 * */