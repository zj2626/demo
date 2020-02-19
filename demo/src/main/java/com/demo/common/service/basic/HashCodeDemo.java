package com.demo.common.service.basic;

import org.junit.Test;

public class HashCodeDemo {

    @Test
    public void test(){
        String a = new String("hhh");
        String b = new String("hhh");

        System.out.println(a == b);

        // System.identityHashCode方法是java根据对象在内存中的地址算出来的一个数值，不同的地址算出来的结果是不一样的。因此这里打印出的结果不一样
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));

        // String重写了hashCode导致相同
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
    }
}
