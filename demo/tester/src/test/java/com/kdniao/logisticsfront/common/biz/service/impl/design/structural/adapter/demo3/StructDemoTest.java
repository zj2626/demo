package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.demo3;

import org.junit.Test;

/**
 * 缺省适配器(Default Adapter Pattern)
 * 当不需要实现一个接口所有的方法的时候，可以先设计一个抽象适配器实现该接口，并且该抽象类为接口中的每一个方法提供一个默认实现(空方法)，然后改抽象类的子类再选择性地覆盖父类的方法来满足需求。
 */
public class StructDemoTest {

    @Test
    public void test1() {
        BigPort bigPort = new SmallToBigAdapter(new SmallPortFacility());
        bigPort.userBigPort();
    }
}
