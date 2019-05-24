package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.strategy.design.map;

// 环境类
public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void algorithm() {
        strategy.algorithm();
    }
}
