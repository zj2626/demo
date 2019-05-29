package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.visitor.design.map;

// 具体访问者：实现对每一具体元素的访问方法。
public class ConcreteVisitor2 implements Visitor {
    @Override
    public void visit(Element element) {
        System.out.println("ConcreteVisitor2 的visit方法");
    }
}
