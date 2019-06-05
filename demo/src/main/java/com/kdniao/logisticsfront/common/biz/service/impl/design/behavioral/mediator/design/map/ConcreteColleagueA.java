package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator.design.map;

// 具体同事类
public class ConcreteColleagueA extends Colleague {
    public ConcreteColleagueA(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void getMessage(String message) {
        System.out.println("同事A" + name + "获得信息" + message);
    }

    //同事A与中介者通信
    public void contact(String message) {
        mediator.contact(message, this);
    }
}
