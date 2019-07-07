package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.singleton;

import java.io.Serializable;

public class Singleton6 implements Serializable {
    private static final Singleton6 serializable = new Singleton6();

    private Singleton6(){}

    public static Singleton6 getInstance() {
        return serializable;
    }

    public void getMsg() {
        System.out.println(this.getClass().getName() + "-" + hashCode());
    }

    // 实现反序列化之后和序列化前保持单例，则需要实现readResolve方法
    private  Object readResolve(){
        return  serializable;
    }
}
