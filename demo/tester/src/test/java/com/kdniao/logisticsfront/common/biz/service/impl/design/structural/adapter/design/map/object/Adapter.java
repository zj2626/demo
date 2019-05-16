package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.object;

// 适配器类
public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request(){
        adaptee.specificRequest();
    }
}
