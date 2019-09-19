package com.demo.common.service.design.behavioral.visitor.design.map;

// 具体访问者：实现对每一具体元素的访问方法。
public class ConcreteVisitor2 implements Visitor {
    @Override
    public void visit(ConcreteElement element) {
        System.out.println("ConcreteVisitor2 ConcreteElement的visit方法");
    }

    @Override
    public void visit(ConcreteElement2 element) {
        System.out.println("ConcreteVisitor2 ConcreteElement2的visit方法");
    }
}
