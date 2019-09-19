package com.demo.common.service.design.structural.proxy.demo3;

public class RealSubject  {
    public String request(String parameter){
        System.out.println("real request " + parameter);

        return "success";
    }

    public String request2(String parameter){
        System.out.println("real request2 " + parameter);

        return "success";
    }

    public static String fuck(String parameter){
        System.out.println("real fuck " + parameter);

        return "success";
    }
}
