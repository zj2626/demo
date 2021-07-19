package com.demo.common.service.spring.learn3.proxy.static2;

public class TargetClass implements TargetInterface{

    @Override
    public void action1(){
        System.out.println("TargetClass action1");
    }

    @Override
    public void action2(){
        System.out.println("TargetClass action2");
    }
}
