package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.memento.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        CareTaker careTaker = new CareTaker();

        Originator originator = new Originator();
        originator.setState("状态1");
        System.out.println(originator.getState());

        originator.setState("状态2");
        careTaker.add(originator.saveToMemento());
        System.out.println(originator.getState());

        originator.setState("状态3");
        careTaker.add(originator.saveToMemento());
        System.out.println(originator.getState());

        originator.setState("状态4");
        System.out.println(originator.getState());

        originator.setState("状态5");
        System.out.println(originator.getState());

        System.out.println("################");
        System.out.println(originator.getFromMemento(careTaker.get(0)));
        System.out.println(originator.getFromMemento(careTaker.get(1)));
    }
}
