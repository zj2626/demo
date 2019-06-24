package com.kdniao.logisticsfront.common.biz.service.impl.basic;

import org.junit.Test;

public class SubstringDemo {
    @Test
    public void test() {
        String str = "abcdefghijklmn";

        System.out.println(str.substring(5));
        System.out.println(str.substring(0, 5));
        System.out.println(str.substring(5, str.length() - 1));
    }
}
