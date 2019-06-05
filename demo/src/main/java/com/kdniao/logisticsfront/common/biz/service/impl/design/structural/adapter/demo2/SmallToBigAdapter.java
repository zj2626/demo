package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.demo2;

/**
 * 把小口转换为大口 (SmallPort是Adaptee适配者类, BigPort是target目标类)
 * <p>
 * 对象适配器模式
 * <p>
 * 功能: 某设备提供的是小口的插头,而客户端使用大口的插头,所以在转换类中将大口转换为小口使用(类似电源适配器)
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
