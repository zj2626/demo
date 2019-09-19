package com.demo.common.service.design.creational.singleton;

public class Singleton3 {
    private static final Singleton3 instance = new Singleton3();

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return instance;
    }

    public void getMsg() {
        System.out.println(this.getClass().getName() + '\n');
    }
}
