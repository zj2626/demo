package com.demo.common.service.spring.learn3.proxy.static2;

public class ProxyClass2 implements TargetInterface {
    TargetInterface target;

    public ProxyClass2(TargetInterface target){
        this.target = target;
    }

    public void action1() {
        System.out.println("before2");
        target.action1();
        System.out.println("after2");
    }

    public void action2() {
        System.out.println("before2");
        target.action1();
        System.out.println("after2");
    }
}
