package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.singleton;

public class Singleton {
    private static Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }

    public void getMsg(){
        System.out.println(this.getClass().getName() + '\n');
    }
}
