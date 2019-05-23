package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator.design.map;

// 具体中介者
public class ConcreteMediator extends Mediator {
    private ConcreteColleagueA concreteColleagueA;
    private ConcreteColleagueB concreteColleagueB;

    // 聊天室
    @Override
    public void contact(String content, Colleague coll) {
        if (coll == concreteColleagueA) {
            concreteColleagueB.getMessage(content);
        } else {
            concreteColleagueA.getMessage(content);
        }
    }

    public ConcreteColleagueA getConcreteColleagueA() {
        return concreteColleagueA;
    }

    public void setConcreteColleagueA(ConcreteColleagueA concreteColleagueA) {
        this.concreteColleagueA = concreteColleagueA;
    }

    public ConcreteColleagueB getConcreteColleagueB() {
        return concreteColleagueB;
    }

    public void setConcreteColleagueB(ConcreteColleagueB concreteColleagueB) {
        this.concreteColleagueB = concreteColleagueB;
    }
}
