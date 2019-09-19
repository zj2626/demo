package com.demo.common.service.design.behavioral.state.design.map;

public class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void request() {
        state.handle(this);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

/*
状态模式

State: 抽象状态类，定义一个接口以封装与context的一个状态相关的行为
ConcreteState: 具体状态，每一子类实现一个与Context的一个状态相关的行为
Context: 维护一个ConcreteState子类的实例，这个实例定义当前的状态。
 */