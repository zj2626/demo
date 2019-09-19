package com.demo.common.service.design.structural.adapter.demo2;

import org.junit.Test;

/**
 * 部分电脑不支持投影仪的大口 需要使用适配器把小口转换为大口
 */
public class StructDemoTest {

    @Test
    public void test1() {
        BigPort bigPort = new SmallToBigAdapter(new SmallPortFacility());
        bigPort.userBigPort();
    }
}
/**
 * 适配器模式包含四个角色：
 * 目标抽象类定义客户要用的特定领域的接口；
 * 适配器类可以调用另一个接口，作为一个转换器，对适配者和抽象目标类进行适配，它是适配器模式的核心；
 * 适配者类是被适配的角色，它定义了一个已经存在的接口，这个接口需要适配；
 * 在客户类中针对目标抽象类进行编程，调用在目标抽象类中定义的业务方法。
 */
