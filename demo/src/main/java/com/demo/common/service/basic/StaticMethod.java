package com.demo.common.service.basic;

import org.junit.Test;

/**
 * @author zj2626
 * @name StaticMethod
 * @description
 * @create 2021-07-10 16:35
 **/
public class StaticMethod {

    public static int A = 1;

    static {
        A = 3;
        B = 4;
        C = 5;
        System.out.println("static=>" + A);
        // *非法向前引用变量* : 静态语句块中只能访问到定义在静态语句块之前的变量，定义在它之后的变量，在前面的静态语句块可以赋值，但是不能访问
        // System.out.println("static=>" + B);
    }

    public static int B = 2;

    public final static int C;

    @Test
    public void test() {
        System.out.println(StaticMethod.A);
        System.out.println(StaticMethod.B);
        System.out.println(StaticMethod.C);
    }
}
