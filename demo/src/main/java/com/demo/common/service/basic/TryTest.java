package com.demo.common.service.basic;

public class TryTest {
    public static void main(String[] args) {
        //        System.out.println(fun());
        //        System.out.println(fun2());
        //        System.out.println(fun3());
        //        System.out.println(fun4());
        System.out.println(fun5());
    }

    private static int fun() {
        int result = 10;
        try {
            return result;
        } finally {
            result = result + 10;
            return result + 100;    // 有return会生效, 没有return则不会生效
        }
    }

    private static StringBuilder fun2() {
        StringBuilder result = new StringBuilder("2");
        try {
            return result;
        } finally {
            result.append("333");   // 会生效
            result = null;          // 不会生效
        }
    }

    private static Person fun3() {
        Person result = new Person();
        try {
            result.name = "zj";
            return result;
        } finally {
            result.name = "ay"; // 会生效
            result = null;      // 不会生效
        }
    }

    private static Person fun4() {
        Person result = new Person();
        try {
            result.name = "zj";
            return result;
        } finally {
            result.name = "ay"; // 会生效
            result = null;      // 会生效
            return result;
        }
    }

    private static class Person {
        String name;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }

    private static Object fun5() {
//        String result = "[";
//        try {
//            result += "a";
//            return result += "b";
//        } finally {
//            result += "c";
//        }

        Person result2 = new Person();
        try {
            result2.name = "a";
            return result2;
        } finally {
            result2.name = "b";
        }
    }
}
