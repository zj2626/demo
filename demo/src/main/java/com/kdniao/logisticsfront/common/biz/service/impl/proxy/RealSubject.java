package com.kdniao.logisticsfront.common.biz.service.impl.proxy;

// 必须实现接口
public class RealSubject extends Parent implements Subject {
    @Override
    public String requestA(String parameter){
        System.out.println("real requestA " + parameter);

        return "success";
    }

    @Override
    public String requestB(String parameter, String parameter2){
        System.out.println("real requestB " + parameter + " - " + parameter2);

        return "success";
    }

    @Override
    public void cannotUse(){
        System.out.println("ffffffff i got you haha");
    }
}
