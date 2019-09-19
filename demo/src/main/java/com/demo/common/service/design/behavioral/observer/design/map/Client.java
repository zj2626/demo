package com.demo.common.service.design.behavioral.observer.design.map;

import org.junit.Test;

public class Client {

    @Test
    public void test1() {
        Subject subject = new ConcreteSubject();

        Observer observer1 = new ConcreteObserver();
        Observer observer2 = new ConcreteObserver();
        Observer observer3 = new ConcreteObserver();
        subject.attach(observer1);
        subject.attach(observer2);
        subject.attach(observer3);

        subject.changeStatus();
    }

}
