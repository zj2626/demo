package com.demo.common.service.math;

import org.junit.Test;

/**
 * *** 运算符 ***
 */
public class OperatorDemo {

    /**
     * 位运算符
     */
    @Test
    public void number() {
        int a = 60; /*                  60 = 0011 1100 */
        int b = 13; /*                  13 = 0000 1101 */
        int c = -12;/*                 -12 = 1000 1100
                                     -12补码  1111 0100 (在计算机系统中，数值一律用补码来表示和存储 -> 取反后加1)*/

        // &    如果相对应位都是1，则结果为1，否则为0
        /*                              12 = 0000 1100 */
        /*                              52 = 0011 0100 */
        System.out.println(a & b);
        System.out.println(a & c);
        System.out.println("--------------------------");

        // |    如果相对应位都是 0，则结果为 0，否则为 1
        /*                              61 = 0011 1101 */
        /*                              -4 = 1111 1100 求原码:取反后加1 1000 0100*/
        System.out.println(a | b);
        System.out.println(a | c);
        System.out.println("--------------------------");

        // ^    如果相对应位值相同，则结果为0，否则为1
        /*                              49 = 0011 0001 */
        /*            -56 = 1100 1000 求原码:取反后加1 1011 1000*/
        System.out.println(a ^ b);
        System.out.println(a ^ c);
        System.out.println("--------------------------");

        // ~    按位取反运算符翻转操作数的每一位，即0变成1，1变成0。
        /*                             -61 = 1100 0011 */
        /*                             -60             */
        /*                             -13             */
        /*                              11 = 0000 1011 */
        System.out.println(~a);
        System.out.println(~a + 1);
        System.out.println(~b + 1);
        System.out.println(~c);
        System.out.println("--------------------------");

        // <<    按位左移运算符。左操作数按位左移右操作数指定的位数 被用来做 * (2 ^ n)的运算
        /*                             240 = 1111 0000 */
        /*                             120 = 0111 0000 */
        /*                             -24 = 1110 1000 求原码:取反后加1 1001 1000*/
        System.out.println(a << 2); // 左移2位 相当于乘4
        System.out.println(a << 1); // 左移1位 相当于乘2
        System.out.println(c << 1);
        System.out.println("--------------------------");

        // >>    按位右移运算符。左操作数按位右移右操作数指定的位数。 被用来做 / (2 ^ n)的运算
        /*                              15 = 0000 1111 */
        /*                              30 = 0001 1110 */
        /*                              -6 = 0111 1010 求原码:取反后加1 1000 0110*/
        System.out.println(a >> 2); // 右移2位 相当于除4
        System.out.println(a >> 1); // 左移1位 相当于除2
        System.out.println(c >> 1);
        System.out.println("--------------------------");

        // >>>: 按位右移补零操作符。左操作数的值按右操作数指定的位数右移，移动得到的空位以零填充。
        /*                              15 = 0000 1111 */
        /*                              30 = 0001 1110 */
        /* x = 0111 1111 1111 1111 1111 1111 0111 0010*/
        System.out.println(a >>> 2);
        System.out.println(a >>> 1);
        System.out.println(c >>> 1);
        System.out.println(97 ^ (97 >>> 16));
    }

    /**
     * 赋值运算符
     */
    @Test
    public void number2() {
        int a = 60; /* 60 = 0011 1100 */
        int b = 13; /* 13 = 0000 1101 */

        // =
        // +=
        // -=
        // *=
        // /=
        // %=
        // %=

        System.out.println(a <<= 2);
        System.out.println(a >>= 2);
        System.out.println(a |= 2);
        System.out.println(a &= 2);
        System.out.println(a ^= 2);
    }


    /**
     * 位运算用法
     */
    @Test
    public void number3() {
        int a = 60; /* 60 = 0011 1100 */
        int b = 13; /* 13 = 0000 1101 */

        // 判断奇偶数
        System.out.println((a & 1) == 0);
        System.out.println("--------------------------");

        // 取余 对2^n的数值进行取余计算
        int n = 8 - 1;
        System.out.println(a & n);
        System.out.println(n & a);
        System.out.println("--------------------------");

        // 生成第一个大于a的满足2^n的数
        System.out.println(tableSizeFor(5));
        System.out.println("--------------------------");

        // 相反数
        System.out.println(~a + 1);
        System.out.println("--------------------------");

        // 绝对值
        System.out.println(a >> 31 == 0 ? a : (~a + 1));
        System.out.println("--------------------------");

        // 交换两个数
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println(a);
        System.out.println(b);
        System.out.println("--------------------------");
    }

    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 :  n + 1;
    }
}
