package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.visitor.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        ObjectStrust objectStrust = new ObjectStrust();

        objectStrust.add(new ConcreteElement());
        objectStrust.add(new ConcreteElement());

        objectStrust.accept(new ConcreteVisitor());

        objectStrust.accept(new ConcreteVisitor2());
    }
}
