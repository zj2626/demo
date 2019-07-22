package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.delegate;

public class ExcuterA implements IExcuter {
    @Override
    public void excute(String command) {
        System.out.println("a 员工 " + command);
    }
}
