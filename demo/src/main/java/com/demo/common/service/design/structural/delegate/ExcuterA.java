package com.demo.common.service.design.structural.delegate;

public class ExcuterA implements IExcuter {
    @Override
    public void excute(String command) {
        System.out.println("a 员工 " + command);
    }
}
