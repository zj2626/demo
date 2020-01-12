package com.demo.common.service.spring.learn3.proxy.static1;

public class ProxyClass extends TargetClass {

    public void action1() {
        System.out.println("before");
        super.action1();
        System.out.println("after");
    }

    public void action2() {
        System.out.println("before");
        super.action1();
        System.out.println("after");
    }
}
