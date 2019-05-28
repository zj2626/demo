package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator.design.map;

// 抽象中介者 中介者定义一个接口用于与各同事（Colleague）对象通信。
public abstract class Mediator {
    public abstract void contact(String content,Colleague coll);
}
