package com.demo.common.service.basic;

/**
 * @author zj2626
 * @name ExtendsTest
 * @description
 * @create 2020-11-04 11:25
 **/
public class ExtendsTest {

    public static void main(String[] args) {
//        C c = new C();
//        System.out.println(c);

        System.out.println(C.abc);
    }
}

class P {
    public static int abc = 123;

    static {
        System.out.println("PPPP");
    }
}

class C extends P {
    static {
        System.out.println("CCCC"); // 不执行
    }
}
