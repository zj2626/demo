package com.demo.common.service.design.structural.bridge.design.map;

//抽象类
public abstract class Abstraction {

    protected Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    //示例方法
    public void operation() {

        implementor.operationImpl();
    }

    public abstract void otherOperation();
}