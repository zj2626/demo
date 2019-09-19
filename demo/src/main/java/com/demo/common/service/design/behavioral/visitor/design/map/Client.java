package com.demo.common.service.design.behavioral.visitor.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        ObjectStrust objectStrust = new ObjectStrust();

        objectStrust.add(new ConcreteElement());
        objectStrust.add(new ConcreteElement2());

        objectStrust.accept(new ConcreteVisitor());
        objectStrust.accept(new ConcreteVisitor2());
    }
}
