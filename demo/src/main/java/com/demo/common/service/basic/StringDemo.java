package com.demo.common.service.basic;

import org.junit.Test;

public class StringDemo {
    /**
     * String类的intern()方法：一个初始为空的字符串池，它由类String独自维护。
     * 当调用 intern方法时，如果池已经包含一个等于此String对象的字符串（用equals(oject)方法确定），则返回池中的字符串。
     * 否则，将此String对象添加到池中，并返回此String对象的引用。 
     * 对于任意两个字符串s和t，当且仅当s.equals(t)为true时，s.intern() == t.intern()才为true。
     * 所有字面值字符串和字符串赋值常量表达式都使用 intern方法进行操作。
     *
     * see https://blog.csdn.net/soonfly/article/details/70147205
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

        String m1 = new char[]{'a', 'b'}.toString();
        String m2 = new StringBuilder("a").append("b").toString();
        String n = 'a' + 'b' + "";
        String o = "a" + "b" + "";

        String t1 = "a";
        String t2 = "b";
        String p = t1 + t2;

        final String t3 = "a";
        final String t4 = "b";
        String q = t3 + t4;

        System.out.println(" 为true的");
        System.out.println(a == b);
        System.out.println(a == e);
        System.out.println(e == f);
        System.out.println(a == c);
        System.out.println(c == d);
        System.out.println(c == e);
        System.out.println();
        System.out.println(" 为false的");
        System.out.println(a == g);
        System.out.println(c == g);
        System.out.println(e == g);
        System.out.println(g == h);
        System.out.println();
        System.out.println(" m1的");
        System.out.println(m1 == a);
        System.out.println(m1 == c);
        System.out.println(m1 == e);
        System.out.println(m1 == g);
        System.out.println();
        System.out.println(" m2的");
        System.out.println(m2 == a);
        System.out.println(m2 == c);
        System.out.println(m2 == e);
        System.out.println(m2 == g);
        System.out.println();
        System.out.println(" n的");
        System.out.println(n == a);
        System.out.println(n == c);
        System.out.println(n == e);
        System.out.println(n == g);
        System.out.println();
        System.out.println(" o的");
        System.out.println(o == a);
        System.out.println(o == c);
        System.out.println(o == e);
        System.out.println(o == g);
        System.out.println(o == p);
        System.out.println(o == q);
        System.out.println();
        System.out.println(" intern的");
        System.out.println("a: " + (a.intern() == a));
        System.out.println("g: " + (g.intern() == g));
        System.out.println("m1: " + (m1.intern() == m1));
        System.out.println("m2: " + (m2.intern() == m2));
        System.out.println("n: " + (n.intern() == n));
        System.out.println("o: " + (o.intern() == o));
        System.out.println("p: " + (p.intern() == p));
        System.out.println("q: " + (q.intern() == q));
    }

    @Test
    public void test2() {
        String str2 = new String("str") + new String("01");
        str2.intern();
        String str1 = "str01";
        System.out.println(str2 == str1); // true
    }

    @Test
    public void test3() {
        String str2 = new String("str") + new String("01");
        String str1 = "str01";
        str2.intern();
        System.out.println(str2 == str1); // false
    }

    @Test // TODO
    public void test4() {
        // jdk1.7之后, intern()实现不会再复制实例，只是在常量池中记录首次出现的实例引用，因此intern()返回的引用和由StringBuilder创建的那个字符串实例是同一个
        String str1 = new StringBuilder("aa").append("bb").toString();
        System.out.println(str1.intern() == str1); // true

        String str2 = new StringBuilder("aa").append("bb").toString();
        System.out.println(str2.intern() == str2); // false

        // “java”这个字符串在执行StringBuilder.toString（）之前已经出现过，字符串常量池中已经有它的引用了
        String str3 = new StringBuilder("ja").append("va").toString();
        System.out.println(str3.intern() == str3); // false
    }
}
