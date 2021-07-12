package com.demo.common.service.basic;

import org.junit.Test;

/*代码示例 动态分派 重写*/
public class DynamicDispatch {
    static abstract class Human {
        public void sayHello() {
            System.out.println("hello,Human guy！");
        }
    }

    static class Man extends Human {
        @Override
        public void sayHello() {
            System.out.println("hello,Man guy！");
        }
    }

    static class Woman extends Human {
        @Override
        public void sayHello() {
            System.out.println("hello,Woman guy！");
        }
    }

    @Test
    public void test1() {
        Human human = new Man();
        human.sayHello();
        human = new Woman();
        human.sayHello();
    }
}
