package com.demo.common.service.basic.bean;

public class Father {

    public String notStaticStr = getNotStaticStr();
    public static String staticStr = getStaticStr();
    public final String finalStaticStr = getFinalStr();

    static {
        System.out.println("Father--static");
    }

    {
        System.out.println("Father--not static");
    }

    public static String getNotStaticStr(){
        System.out.println("Father--notStaticStr");
        return "notStaticStr";
    }

    public static String getStaticStr(){
        System.out.println("Father--staticStr");
        return "staticStr";
    }

    public static String getFinalStr(){
        System.out.println("Father--finalStr");
        return "finalStr";
    }

    public Father() {
        System.out.println("Father--constructor");
        System.out.println();
    }

    public static class StaticInFather {
        public static String staticStr = getStaticStr();
        public final String finalStaticStr = getFinalStr();
        public String notStaticStr = getNotStaticStr();

        static {
            System.out.println("StaticInFather--static");
        }

        {
            System.out.println("StaticInFather--not static");
        }

        public static String getNotStaticStr(){
            System.out.println("StaticInFather--notStaticStr");
            return "notStaticStr";
        }

        public static String getStaticStr(){
            System.out.println("StaticInFather--staticStr");
            return "staticStr";
        }

        public static String getFinalStr(){
            System.out.println("StaticInFather--finalStr");
            return "finalStr";
        }

        public StaticInFather() {
            System.out.println("StaticInFather--constructor");
            System.out.println();
        }
    }
}
