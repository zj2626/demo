package com.demo.common.service.design.creational.singleton;

public class Singleton2 {
    private Singleton2() {
    }

    private static class SingletonClassInstance {
        private static final Singleton2 instance = new Singleton2();
    }

    public static Singleton2 getInstance() {
        return SingletonClassInstance.instance;
    }

    public void getMsg() {
        System.out.println(this.getClass().getName() + '\n');
    }
}
