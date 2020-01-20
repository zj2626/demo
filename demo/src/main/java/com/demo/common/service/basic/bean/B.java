package com.demo.common.service.basic.bean;

public class B {
    static {
        System.out.println("B--static");
    }

    {
        System.out.println("B--not static");
    }

    public B() {
        System.out.println("B--constructor");
    }

    public static class D {
        static {
            System.out.println("D--static");
        }

        {
            System.out.println("D--not static");
        }

        public D() {
            System.out.println("D--constructor");
        }
    }
}
