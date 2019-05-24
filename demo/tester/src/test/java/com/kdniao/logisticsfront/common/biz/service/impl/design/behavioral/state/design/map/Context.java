package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.state.design.map;

public class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void request() {
        state.handle();
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
