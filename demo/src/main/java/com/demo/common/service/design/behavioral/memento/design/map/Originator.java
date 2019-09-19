package com.demo.common.service.design.behavioral.memento.design.map;

// Originator 创建并在 Memento 对象中存储状态
public class Originator {
    private String state;

    public String getState(){
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento saveToMemento(){
        return new Memento(state);
    }

    public String getFromMemento(Memento memento){
        return memento.getState();
    }
}
