package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.singleton;

public class Singleton {
    private static volatile Singleton singleton = null; // volatile防止指令重排,保证变量修改的可见性

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
