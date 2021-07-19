package com.demo.common.service.design.structural.proxy.demo2;

// 必须实现接口
public class RealSubject extends Parent implements Subject {
    @Override
    public String request(String parameter) {
        System.out.println("real request " + parameter);

        return "success";
    }

    @Override
    public String request2(String parameter, String parameter2) {
        System.out.println("real request2 " + parameter + " - " + parameter2);
        request3(parameter);

        return "success";
    }

    @Override
    public String request3(String parameter) {
        System.out.println("real request3 " + parameter);

        return "success";
    }

    @Override
    public void cannotUse() {
        System.out.println("real cannotUse");
    }
}
