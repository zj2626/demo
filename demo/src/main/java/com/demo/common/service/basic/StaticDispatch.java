package com.demo.common.service.basic;

import org.junit.Test;

/*代码示例 静态分派*/
public class StaticDispatch {
    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Woman extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("hello,Human guy！");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,Man guy！");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello,Woman guy！");
    }

    @Test
    public void test1() {
        Human man = new Man();
        Human woman = new Woman();
        this.sayHello(man);
        this.sayHello(woman);
    }


    @Test
    public void test2() {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
