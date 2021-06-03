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
        String a = "ab";
        String b = "ab";
        String c = "ab".intern();
        String d = "ab".intern();
        String e = new String("ab").intern();
        String f = new String("ab").intern();
        String g = new String("ab");
        String h = new String("ab");

        String i = new char[]{'a', 'b'}.toString();
        String k = 'a' + 'b' + "";
        String m = "a" + "b" + "";

        System.out.println(a == b);
        System.out.println(a == e);
        System.out.println(e == f);
        System.out.println(a == c);
        System.out.println(c == d);
        System.out.println(c == e);
        System.out.println();
        System.out.println(a == g);
        System.out.println(c == g);
        System.out.println(e == g);
        System.out.println(g == h);
        System.out.println();
        System.out.println();
        System.out.println(i == a);
        System.out.println(i == c);
        System.out.println(i == e);
        System.out.println(i == g);
        System.out.println();
        System.out.println(k == a);
        System.out.println(k == c);
        System.out.println(k == e);
        System.out.println(k == g);
        System.out.println();
        System.out.println(m == a);
        System.out.println(m == c);
        System.out.println(m == e);
        System.out.println(m == g);
    }
}
