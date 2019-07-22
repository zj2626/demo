package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.delegate;

public class ExcuterB implements IExcuter {
    @Override
    public void excute(String command) {
        System.out.println("b 员工 " + command);
    }
}
