package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.state;

import org.junit.Test;

/**
 * 4. 状态模式(State Pattern)
 * <p>
 * 状态模式(State Pattern) ：允许一个对象在其内部状态改变时改变它的行为，对象看起来似乎修改了它的类。
 * 其别名为状态对象(Objects for States)，状态模式是一种对象行为型模式。
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
 * 4.8. 优点
 * 状态模式的优点
 * <p>
 * 封装了转换规则。
 * 枚举可能的状态，在枚举状态之前需要确定状态种类。
 * 将所有与某个状态有关的行为放到一个类中，并且可以方便地增加新的状态，只需要改变对象状态即可改变对象的行为。
 * 允许状态转换逻辑与状态对象合成一体，而不是某一个巨大的条件语句块。
 * 可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数。
 * 4.9. 缺点
 * 状态模式的缺点
 * <p>
 * 状态模式的使用必然会增加系统类和对象的个数。
 * 状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱。
 * 状态模式对“开闭原则”的支持并不太好，对于可以切换状态的状态模式，增加新的状态类需要修改那些负责状态转换的源代码，否则无法切换到新增状态；而且修改某个状态类的行为也需修改对应类的源代码。
 */
