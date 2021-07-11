package com.demo.common.service.basic;

/**
 * @author zj2626
 * @name ExtendsTest
 * @description
 * @create 2020-11-04 11:25
 **/
public class ExtendsTest {

    public static void main(String[] args) {
        System.out.println(C.abc);

        System.out.println("==================");

        C c = new C();
        System.out.println(c);
        c.pr();
    }
}

class P {
    public static int abc = 123;

    static {
        System.out.println("PPPP");
    }
}

class C extends P {
    private static int A;
    private int B;

    static {
        System.out.println("CCCC"); // 不执行
    }

    public void pr(){
        System.out.println(A);
        System.out.println(B);
    }
}
