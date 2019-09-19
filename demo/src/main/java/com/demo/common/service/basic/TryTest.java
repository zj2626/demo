package com.demo.common.service.basic;

public class TryTest {
    public static void main(String[] args) {
        System.out.println(fun());
        System.out.println(fun2());
        System.out.println(fun3());
    }

    private static int fun(){
        int result = 10;
        try{
            return result;
        }finally {
            result = result + 10;
            return result + 100;    // 有return会生效, 没有return则不会生效
        }
    }

    private static StringBuilder fun2(){
        StringBuilder result = new StringBuilder("2");
        try{
            return result;
        }finally {
            result.append("333");   // 会生效
            result = null;          // 不会生效
        }
    }

    private static Person fun3(){
        Person result = new Person();
        try{
            result.name = "zj";
            return result;
        }finally {
            result.name = "ay"; // 会生效
            result = null;      // 不会生效
        }
    }

    private static class Person{
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
}
