package com.demo.common.service.design.structural.adapter.demo3;

/**
 * 把小口转换为大口 (SmallPort是Adaptee适配者类, BigPort是target目标类)
 * <p>
 * 对象适配器模式
 */
public class SmallToBigAdapter implements BigPort {
    private AbstractSmallPort smallPort;//小口

    public SmallToBigAdapter(AbstractSmallPort smallPort) {
        this.smallPort = smallPort;
    }

    @Override
    public void userBigPort() {
        System.out.println("表面上 这里调用的是大口:userBigPort 实际上:userSmallPort \n");

        smallPort.userSmallPort();

        smallPort.userSmallPort2();
    }
}
