package com.demo.common.service.design.behavioral.observer.design.map;

// 具体观察者
public class ConcreteObserver implements Observer {
    @Override
    public void update() {
        System.out.println(Thread.currentThread().getName() + " get message !!!");
    }
}
