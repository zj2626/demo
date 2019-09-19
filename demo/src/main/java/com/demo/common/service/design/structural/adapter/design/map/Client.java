package com.demo.common.service.design.structural.adapter.design.map;


import com.demo.common.service.design.structural.adapter.design.map.object.Adapter;
import com.demo.common.service.design.structural.adapter.design.map.object.Target;
import com.demo.common.service.design.structural.adapter.design.map.object.Adaptee;

public class Client {
    public static void main(String[] args) {
        com.demo.common.service.design.structural.adapter.design.map.clazz.Target target = new com.demo.common.service.design.structural.adapter.design.map.clazz.Adapter();
        target.request();

        Target target2
                = new Adapter(new Adaptee());
        target2.request();
    }
}
