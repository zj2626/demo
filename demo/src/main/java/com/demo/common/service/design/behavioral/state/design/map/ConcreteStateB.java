package com.demo.common.service.design.behavioral.state.design.map;

public class ConcreteStateB implements State {

    @Override
    public String getState() {
        return "状态2";
    }

    @Override
    public void handle(Context context) {
        System.out.println("b hadle ");
    }
}
