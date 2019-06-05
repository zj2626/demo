package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator;

import org.junit.Test;

/**
 * 2.中介者模式(Mediator Pattern)
 * <p>
 * 中介者模式(Mediator Pattern)定义：用一个中介对象来封装一系列的对象交互，
 * 中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 * 中介者模式又称为调停者模式，它是一种对象行为型模式。
 * <p>
 * 举例:
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/22 18:30 zhangj Exp $
 */

public class StructDemoTest {

    @Test
    public void test1() {
    }

}
/**
 * 2.8. 优点
 * 中介者模式的优点
 * <p>
 * 简化了对象之间的交互。
 * 将各同事解耦。
 * 减少子类生成。
 * 可以简化各同事类的设计和实现。
 * 2.9. 缺点
 * 中介者模式的缺点
 * <p>
 * 在具体中介者类中包含了同事之间的交互细节，可能会导致具体中介者类非常复杂，使得系统难以维护。
 */
