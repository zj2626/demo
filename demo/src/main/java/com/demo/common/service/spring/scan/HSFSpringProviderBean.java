package com.demo.common.service.spring.scan;

public class HSFSpringProviderBean {

    public void setTarget(Object obj){
        System.out.println("setTarget " + obj);
    }

    public void setServiceInterface(Object obj){
        System.out.println("setServiceInterface" + obj);
    }
}
