package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.observer;

import org.junit.Test;

/**
 * 3. 观察者模式(Observer Pattern)
 * <p>
 * 观察者模式(Observer Pattern)：定义对象间的一种一对多依赖关系，使得每当一个对象状态发生改变时，其相关依赖对象皆得到通知并被自动更新。
 * 观察者模式又叫做发布-订阅（Publish/Subscribe）模式、模型-视图（Model/View）模式、源-监听器（Source/Listener）模式或从属者（Dependents）模式。
 * <p>
 * 观察者模式是一种对象行为型模式。
 * <p>
 * 举例: AWT和Swing中的监听机制
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/22 18:30 zhangj Exp $
 */

public class StructDemoTest {

    @Test
    public void test1() throws InterruptedException {
        Child child = new Child();

        WakeUpListener dad = new Dad();
        WakeUpListener dog = new Dog();
        WakeUpListener grand = new Grand();

        child.addListener(dog);
        child.addListener(dad);
        child.addListener(grand);

        new Thread(child).start();
        Thread.sleep(2000);
    }

    @Test
    public void test2() throws InterruptedException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Child child = new Child();

        String[] observerStrs = PropertyMgr.getProps("observers").split(",");
        for(String observerStr : observerStrs){
            child.addListener(
                    (WakeUpListener) Class.forName(observerStr).newInstance());
        }

        new Thread(child).start();
        Thread.sleep(2000);
    }

}
/**
 * 3.8. 优点
 * 观察者模式的优点
 * <p>
 * 观察者模式可以实现表示层和数据逻辑层的分离，并定义了稳定的消息更新传递机制，抽象了更新接口，使得可以有各种各样不同的表示层作为具体观察者角色。
 * 观察者模式在观察目标和观察者之间建立一个抽象的耦合。
 * 观察者模式支持广播通信。
 * 观察者模式符合“开闭原则”的要求。
 * 3.9. 缺点
 * 观察者模式的缺点
 * <p>
 * 如果一个观察目标对象有很多直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间。
 * 如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。
 * 观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。
 */
