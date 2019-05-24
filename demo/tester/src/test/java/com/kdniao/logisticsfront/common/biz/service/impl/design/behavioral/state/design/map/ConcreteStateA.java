package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.state.design.map;

public class ConcreteStateA implements State {

    @Override
    public String getState() {
        return "状态1";
    }

    @Override
    public void handle(Context context) {
        System.out.println("a hadle ");

        context.changeState(new ConcreteStateB());
    }
}
