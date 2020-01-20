package com.demo.common.service.basic.bean;

public class A extends B {
    static {
        System.out.println("A--static");
    }

    {
        System.out.println("A--not static");
    }

    public A() {
        System.out.println("A--constructor");
    }

    public static class C {
        static {
            System.out.println("C--static");
        }

        {
            System.out.println("C--not static");
        }

        public C() {
            System.out.println("C--constructor");
        }
    }
}
