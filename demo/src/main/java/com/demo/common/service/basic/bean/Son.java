package com.demo.common.service.basic.bean;

public class Son extends Father {
    public String notStaticStr = getNotStaticStr();
    public static String staticStr = getStaticStr();
    public final String finalStaticStr = getFinalStr();

    static {
        System.out.println("Son--static");
        System.out.println("===1");
    }

    {
        System.out.println("Son--not static");
    }

    public static String getNotStaticStr(){
        System.out.println("Son--notStaticStr");
        return "notStaticStr";
    }

    public static String getStaticStr(){
        System.out.println("Son--staticStr");
        return "staticStr";
    }

    public static String getFinalStr(){
        System.out.println("Son--finalStr");
        return "finalStr";
    }

    public Son() {
        System.out.println("Son--constructor");
        System.out.println();
    }

    public static class StaticInSon extends Father.StaticInFather {
        public String notStaticStr = getNotStaticStr();
        public static String staticStr = getStaticStr();
        public final String finalStaticStr = getFinalStr();

        static {
            System.out.println("StaticInSon--static");
            System.out.println("===2");
        }

        {
            System.out.println("StaticInSon--not static");
        }

        public static String getNotStaticStr(){
            System.out.println("StaticInSon--notStaticStr");
            return "notStaticStr";
        }

        public static String getStaticStr(){
            System.out.println("StaticInSon--staticStr");
            return "staticStr";
        }

        public static String getFinalStr(){
            System.out.println("StaticInSon--finalStr");
            return "finalStr";
        }

        public StaticInSon() {
            System.out.println("StaticInSon--constructor");
            System.out.println();
        }
    }
}
