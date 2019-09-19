package com.demo.common.service.design.behavioral.visitor.design.map;

// 抽象元素：声明accept方法，接收访问者的访问操作，通常是抽象类和接口。
public interface Element {
    public void accept(Visitor visitor);
}
