package com.demo.common.service.algorithm.math;

import org.junit.Test;

public class SearchDemo {
    @Test
    public void testHashCode() {
        Integer a = 32;
        System.out.println(a.hashCode());
        Double ab = 32.0;
        System.out.println(ab.hashCode());
        String str = "abc";
        String str2 = "cba";
        System.out.println(str.hashCode());
        System.out.println(str2.hashCode());
    }
}
