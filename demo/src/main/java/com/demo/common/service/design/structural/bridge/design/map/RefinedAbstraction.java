package com.demo.common.service.design.structural.bridge.design.map;

//扩充抽象类
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    //其他的操作方法
    public void otherOperation() {

    }
}