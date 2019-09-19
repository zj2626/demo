package com.demo.common.service.jvm;

public class DemoTest {

    // 获取整数的二进制表示
    public static void main(String[] args) {
        int num = 8;

        for (int i = 0; i < 32; i++) {
            System.out.println((num & 0x80000000 >>> i) >>> (31 - i));
        }
    }
}
