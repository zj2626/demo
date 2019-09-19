package com.demo.common.service.design.structural.adapter.design.map.clazz;

// 适配器类
public class Adapter extends Adaptee implements Target {
    @Override
    public void request(){
        super.specificRequest();
    }
}
