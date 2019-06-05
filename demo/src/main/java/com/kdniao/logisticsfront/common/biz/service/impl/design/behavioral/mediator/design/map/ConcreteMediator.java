package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.mediator.design.map;

// 具体中介者 具体中介者通过协调各同事对象实现协作行为，了解并维护它的各个同事
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
