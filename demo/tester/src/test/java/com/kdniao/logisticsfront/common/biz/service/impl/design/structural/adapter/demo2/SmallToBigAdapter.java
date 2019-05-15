package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.demo2;

/**
 * 把小口转换为大口 (SmallPort是Adaptee适配者类, BigPort是target目标类)
 * <p>
 * 对象适配器模式
 */
public class SmallToBigAdapter implements BigPort {
    private SmallPort smallPort;//小口

    public SmallToBigAdapter(SmallPort smallPort) {
        this.smallPort = smallPort;
    }

    @Override
    public void userBigPort() {
        System.out.print("表面上 这里调用的是大口:userBigPort 实际上:userSmallPort ");
        smallPort.userSmallPort();
    }
}
