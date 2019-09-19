package com.demo.common.service.design.structural.adapter.demo3;


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
