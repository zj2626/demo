package com.demo.common.service.design.structural.proxy.demo2;

public interface Subject {
    Object request(String parameter);

    String request2(String parameter, String parameter2);

    String request3(String parameter);
}
