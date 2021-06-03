package com.demo.common.service.jvm.oom;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 运行时常量池溢出
 * @create 2021-06-01 22:01
 **/
public class RuntimeConstantOOM {

    /**
     * vm args: -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
     */
    public static void main(String[] args) {

    }
}
