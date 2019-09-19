package com.demo.common.service.design.structural.adapter.demo2;

/**
 * 小口的电脑
 */
public class SmallPortFacility implements SmallPort {
    // 使用小口
    public void userSmallPort() {
        System.out.println("使用的小口的电脑");
    }
}
