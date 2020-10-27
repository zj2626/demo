package com.demo.common.service.basic;

import org.junit.Test;

public class StringDemo {
    /**
     * String类的intern()方法：一个初始为空的字符串池，它由类String独自维护。
     * 当调用 intern方法时，如果池已经包含一个等于此String对象的字符串（用equals(oject)方法确定），则返回池中的字符串。
     * 否则，将此String对象添加到池中，并返回此String对象的引用。 
     * 对于任意两个字符串s和t，当且仅当s.equals(t)为true时，s.intern() == t.intern()才为true。
     * 所有字面值字符串和字符串赋值常量表达式都使用 intern方法进行操作。
     */
    @Test
    public void test() {
        String a = "abcde";
        String b = "abcde";
        String c = new String("abcde");
        String d = new String("abcde");

        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(c == d);
        System.out.println();
        System.out.println(b.intern() == a);
        System.out.println(b.intern() == b);
        System.out.println(b.intern() == c);
        System.out.println();
        System.out.println(c.intern() == a);
        System.out.println(c.intern() == c);
        System.out.println(c.intern() == d);
    }

    @Test
    public void test2() {
        String a = "abcde";
        String b = "abcde";
        char[] c = {'a', 'b', 'c', 'd', 'e'};

        System.out.println(a.equals(b));
        System.out.println(a.equals(c.toString()));
    }
}
