package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.delegate;

import org.junit.Test;

/**
 * *. 委派模式(Delegate Pattern)
 * <p>
 * 基本作用就是负责任务的调用和分配任务，跟代理模式很像，可以看做是一种特殊情况下的静态代理的全权代理，但是代理模式注重过程，而委派模式注重结果
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/7/22 10:00 zhangj Exp $
 */
public class StructDemoTest {

    @Test
    public void test1() {
        IExcuter excuter = new Leader();
        excuter.excute("a");
        excuter.excute("b");
    }
}
