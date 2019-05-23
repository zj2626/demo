package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.command;

import org.junit.Test;

/**
 * 1. 命令模式(Command Pattern)
 * <p>
 * 命令模式(Command Pattern)：将一个请求封装为一个对象，从而使我们可用不同的请求对客户进行参数化；
 * 对请求排队或者记录请求日志，以及支持可撤销的操作。
 * 命令模式是一种对象行为型模式，其别名为动作(Action)模式或事务(Transaction)模式。
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
 * 1.8. 优点
 * 命令模式的优点
 * <p>
 * 降低系统的耦合度。
 * 新的命令可以很容易地加入到系统中。
 * 可以比较容易地设计一个命令队列和宏命令（组合命令）。
 * 可以方便地实现对请求的Undo和Redo。
 * 1.9. 缺点
 * 命令模式的缺点
 * <p>
 * 使用命令模式可能会导致某些系统有过多的具体命令类。因为针对每一个命令都需要设计一个具体命令类，因此某些系统可能需要大量具体命令类，这将影响命令模式的使用。
 */
