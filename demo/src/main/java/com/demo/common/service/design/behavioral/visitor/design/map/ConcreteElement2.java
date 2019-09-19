package com.demo.common.service.design.behavioral.visitor.design.map;

// 具体元素：实现accept方法，调用访问者的对应的访问方法。
public class ConcreteElement2 implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void operation() {
        //业务方法
    }
}
