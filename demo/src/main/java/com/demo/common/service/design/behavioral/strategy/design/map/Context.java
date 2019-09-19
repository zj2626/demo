package com.demo.common.service.design.behavioral.strategy.design.map;

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

/*
策略模式

Strategy: 定义所有支持的算法的公共接口抽象类.
ConcreteStrategy: 封装了具体的算法或行为，继承于Strategy
Context: 用一个ConcreteStrategy来配置，维护一个对Strategy对象的引用。
 */