package com.demo.common.service.basic.bean;

public class Father {
    static {
        System.out.println("B--static");
    }

    {
        System.out.println("B--not static");
    }

    public Father() {
        System.out.println("B--constructor");
    }

    public static class StaticInFather {
        static {
            System.out.println("D--static");
        }

        {
            System.out.println("D--not static");
        }

        public StaticInFather() {
            System.out.println("D--constructor");
        }
    }
}
