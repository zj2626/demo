package com.kdniao.logisticsfront.common.biz.service.impl.proxy;

public interface Subject {
    public Object requestA(String parameter);

    public String requestB(String parameter, String parameter2);

    void fatherSMethod();
}
