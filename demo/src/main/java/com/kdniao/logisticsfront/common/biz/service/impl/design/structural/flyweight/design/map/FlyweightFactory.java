package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map;

import java.util.HashMap;

/**
 * 享元工厂类
 */
public class FlyweightFactory {
    private static final HashMap<String, Flyweight> flyweightHashMap = new HashMap<>();

    public static Flyweight getFlyweight(String type) {
        Flyweight flyweight = flyweightHashMap.get(type);

        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(type);
            flyweightHashMap.put(type, flyweight);
        }
        return flyweight;
    }
}
