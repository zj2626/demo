package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.visitor.design.map;

// 抽象访问者：为对象结构中的每一个具体元素类声明一个访问操作。
public interface Visitor {
    public void visit(ConcreteElement element);
    public void visit(ConcreteElement2 element);
}
