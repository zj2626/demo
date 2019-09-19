package com.demo.common.service.design.behavioral.observer.design.map;

import java.util.ArrayList;
import java.util.List;

// 具体目标
public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notice() {
        for(Observer observer : observers){
            observer.update();
        }
    }

    @Override
    public void changeStatus() {
        System.out.println("status is changed ");

        notice();
    }
}
