package com.demo.common.service.basic;

import org.junit.Test;

/**
 * @author zj2626
 * @name StaticMethod
 * @description
 * @create 2021-07-10 16:35
 **/
public class StaticMethod2 {

    @Test
    public void test() {
        System.out.println(Bclass.A); // 只初始化父类
    }

    @Test
    public void test2() {
        System.out.println(Bclass.B);  // 全部初始化
    }
    @Test
    public void test3() {
        System.out.println(Bclass.C);  //  全部初始化
    }

    @Test
    public void test4() {
        System.out.println(Bclass.D);  // 只初始化父接口
    }

    static class Aclass {
        public static int A = 1;
        public static int B = 2;

        static {
            System.out.println("A");
        }
    }

    static class Bclass extends Aclass implements Cclass {
        public static int B = 12;
        public static int C = 13;

        static {
            System.out.println("B");
        }
    }

    interface Cclass {
        public static int D = 10;
    }
}
