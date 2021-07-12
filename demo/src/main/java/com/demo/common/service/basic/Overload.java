package com.demo.common.service.basic;

import java.io.Serializable;

/*代码示例 静态分派 重载的类型转换*/
public class Overload {
    public static void sayHello(Object arg) {
        System.out.println("hello Object");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(Integer arg) {
        System.out.println("hello Integer");
    }

    public static void sayHello(double arg) {
        System.out.println("hello double");
    }

    public static void sayHello(float arg) {
        System.out.println("hello float");
    }

    public static void sayHello(Float arg) {
        System.out.println("hello Float");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(char... arg) {
        System.out.println("hello char……");
    }

    public static void sayHello(int... arg) {
        System.out.println("hello int……");
    }

//    public static void sayHello(Serializable arg) {
//        System.out.println("hello Serializable");
//    }

    public static void main(String[] args) {
        // 类型自动转型(优先级) : 按照char-＞int-＞long-＞float-＞double->Character->Serializable->Object->变长参数(Array) 的顺序转型进行匹配。但不会匹配到byte和short类型的重载，因为char到byte或short的转型是不安全的
        sayHello('a');
        // 1. 调用
        // 2. 注释掉sayHello(char arg)方法  调用
        // 3. 注释掉sayHello(int arg) 方法  调用
        // 3. 注释掉sayHello(long arg)方法  调用
        // 4. 注释掉sayHello(float arg)方法 调用
    }
}
