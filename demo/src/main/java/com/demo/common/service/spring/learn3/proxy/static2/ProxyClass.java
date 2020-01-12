package com.demo.common.service.spring.learn3.proxy.static2;

public class ProxyClass implements TargetInterface {
    TargetInterface target;

    public ProxyClass(TargetInterface target){
        this.target = target;
    }

    public void action1() {
        System.out.println("before");
        target.action1();
        System.out.println("after");
    }

    public void action2() {
        System.out.println("before");
        target.action1();
        System.out.println("after");
    }
}
