package com.demo.common.service.design.behavioral.mediator.design.map;

// 具体同事类
public class ConcreteColleagueB extends Colleague {
    public ConcreteColleagueB(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void getMessage(String message) {
        System.out.println("同事B" + name + "获得信息" + message);
    }

    //同事B与中介者通信
    public void contact(String message) {
        mediator.contact(message, this);
    }
}
