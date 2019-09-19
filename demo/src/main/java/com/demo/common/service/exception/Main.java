package com.demo.common.service.exception;

public class Main {
    public static void main(String[] args) {
        System.out.println("AAA");
        System.out.println("BBB");
        method();
        System.out.println("CCC");
        System.out.println("DDD");
        try {
            method2();

            System.out.println("EEE");
            System.out.println("FFF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void method() {
        System.out.println("SS");
        if (true) {
            throw new RuntimeException("aaa");
        }
        System.out.println("DD");
    }

    private static void method2() throws Exception {
        System.out.println("SS");
        if (true) {
            throw new Exception("aaa");
        }
        System.out.println("DD");
    }
}
