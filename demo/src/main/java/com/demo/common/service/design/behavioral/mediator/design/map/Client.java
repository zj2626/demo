package com.demo.common.service.design.behavioral.mediator.design.map;

import org.junit.Test;


public class Client {

    @Test
    public void test1() {
        ConcreteMediator mediator = new ConcreteMediator();
        ConcreteColleagueA colleagueA = new ConcreteColleagueA("colleagueA", mediator);
        ConcreteColleagueB colleagueB = new ConcreteColleagueB("colleagueB", mediator);
        mediator.setConcreteColleagueA(colleagueA);
        mediator.setConcreteColleagueB(colleagueB);

        colleagueA.contact("fucccccccccccc");

        System.out.println("##########");

        colleagueB.contact("bitchhhhhhhhh");
    }

}
