package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Singleton5 {
    private static Map<String, Singleton5> map = new ConcurrentHashMap<>();

    private Singleton5() {
    }

    public static Singleton5 getInstance() {
        if (!map.containsKey(Singleton5.class.getSimpleName())) {
            synchronized (Singleton5.class) {
                if (!map.containsKey(Singleton5.class.getSimpleName())) {
                    map.put(Singleton5.class.getSimpleName(), new Singleton5());
                }
            }
        }

        return map.get(Singleton5.class.getSimpleName());
    }

    public void getMsg() {
        System.out.println(this.getClass().getName() + "-" + hashCode());
    }
}
