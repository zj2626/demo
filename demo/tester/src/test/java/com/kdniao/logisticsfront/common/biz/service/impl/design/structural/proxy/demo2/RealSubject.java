package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2;

// 必须实现接口
public class RealSubject implements Subject {
    @Override
    public String request(String parameter){
        System.out.println("real request " + parameter);

        return "success";
    }

    @Override
    public String request2(String parameter, String parameter2){
        System.out.println("real request2 " + parameter + " - " + parameter2);

        return "success";
    }
}
