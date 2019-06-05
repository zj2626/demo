package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.observer.design.map;

// 目标
public interface Subject {
    void attach(Observer observer);

    void detach(Observer observer);

    public void notice();

    void changeStatus();
}
