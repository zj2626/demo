package com.demo.common.service.design.structural.proxy.demo1;

public class RealSubject implements Subject {
    @Override
    public String request(String parameter){
        System.out.println("real request " + parameter);

        return "success";
    }
}

