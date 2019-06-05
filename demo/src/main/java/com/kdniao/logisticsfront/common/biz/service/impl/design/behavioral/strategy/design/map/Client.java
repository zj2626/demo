package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.strategy.design.map;

import org.junit.Test;

public class Client {

    @Test
    public void test1() {

        Context context = new Context();
        context.setStrategy(new ConcreteStrategyA());
        context.algorithm();

        context.setStrategy(new ConcreteStrategyB());
        context.algorithm();

    }

}
