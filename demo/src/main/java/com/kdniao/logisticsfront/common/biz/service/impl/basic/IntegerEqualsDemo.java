package com.kdniao.logisticsfront.common.biz.service.impl.basic;

import org.junit.Test;

public class IntegerEqualsDemo {
    /**
     * 由于java虚拟机里面除了字符串常量池还有
     * 一个常用的数字常量池，其范围是-128~127，所以如果Integer指向
     * 这个范围内的数字在编译的时候会直接指向常量池中的数字，而不会创
     * 建新的对象
     */
    @Test
    public void test() {
        Integer a = 98;
        Integer b = 98;
        Integer c = 198;
        Integer d = 198;
        int e = 98;
        int f = 198;

        System.out.println(a == b);
        System.out.println(a == e);
        System.out.println(a.equals(b));
        System.out.println(a.equals(e));
        System.out.println();

        System.out.println(c == d); // false
        System.out.println(c == f);
        System.out.println(c.equals(d));
        System.out.println(c.equals(f));

    }
}
