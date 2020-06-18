package com.demo.common.service.basic.bean;

public class Son extends Father {
    static {
        System.out.println("A--static");
    }

    {
        System.out.println("A--not static");
    }

    public Son() {
        System.out.println("A--constructor");
    }

    public static class StaticInSon {
        static {
            System.out.println("C--static");
        }

        {
            System.out.println("C--not static");
        }

        public StaticInSon() {
            System.out.println("C--constructor");
        }
    }
}
