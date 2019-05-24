package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.state.design.map;

import org.junit.Test;

public class Client {

    @Test
    public void test1() {

        Context context = new Context(new ConcreteStateA());
        System.out.println("##");
        context.request();
        System.out.println("##");
        context.request();
        System.out.println("##");
    }

}
