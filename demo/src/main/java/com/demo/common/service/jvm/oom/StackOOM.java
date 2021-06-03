package com.demo.common.service.jvm.oom;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 堆溢出
 * @create 2021-06-01 22:01
 **/
public class StackOOM {

    private static int stackLength = 1;

    /**
     * vm args: -Xss128k -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        try {
            fun();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("stack length："+ stackLength);
        }
    }

    public static void fun() {
        stackLength++;
        fun();
    }
}
