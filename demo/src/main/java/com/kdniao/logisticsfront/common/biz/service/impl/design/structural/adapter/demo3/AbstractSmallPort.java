package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.demo3;


public abstract class AbstractSmallPort implements SmallPort {
    @Override
    public void userSmallPort() {
        System.out.println("default userSmallPort");
    }

    @Override
    public void userSmallPort2() {
        System.out.println("default userSmallPort2");
    }
}
