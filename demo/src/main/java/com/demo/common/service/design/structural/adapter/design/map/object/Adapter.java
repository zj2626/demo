package com.demo.common.service.design.structural.adapter.design.map.object;

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
