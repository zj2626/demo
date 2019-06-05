package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map;


import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.clazz.Adapter;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.clazz.Target;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.object.Adaptee;

public class Client {
    public static void main(String[] args) {
        Target   target = new Adapter();
        target.request();

        com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.object.Target target2
                = new com.kdniao.logisticsfront.common.biz.service.impl.design.structural.adapter.design.map.object.Adapter (new Adaptee());
        target2.request();
    }
}
