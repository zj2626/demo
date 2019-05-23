package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator.design.map;

// 抽象同事类
public abstract class Colleague {
    protected String name;
    protected Mediator mediator;

    public Colleague(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public abstract void contact(String message);

    public abstract void getMessage(String message);
}
