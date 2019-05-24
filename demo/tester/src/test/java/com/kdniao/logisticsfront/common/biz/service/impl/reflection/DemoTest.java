package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

import org.junit.Test;

import java.lang.reflect.Method;

public class DemoTest {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void test1() throws Exception {
        long total = 500000;

        long now = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            DemoTest demoTest = new DemoTest();
            demoTest.setName("test" + i);
        }
        System.out.println("get - set耗时" + (System.currentTimeMillis() - now) + "ms");

        now = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            Class<?> clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.DemoTest");
            DemoTest demoTest = (DemoTest) clazz.newInstance();
            demoTest.setName("test" + i);
        }
        System.out.println("反射耗时   " + (System.currentTimeMillis() - now) + "ms");

        now = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            Class<?> clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.DemoTest");
            Method method = clazz.getMethod("setName", String.class);
            DemoTest demoTest = new DemoTest();
            method.invoke(demoTest, "test" + i);
        }
        System.out.println("反射耗时   " + (System.currentTimeMillis() - now) + "ms");

        now = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            Class<?> clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.DemoTest");
            DemoTest demoTest = (DemoTest) clazz.newInstance();
            Method method = clazz.getMethod("setName", String.class);
            method.invoke(demoTest, "test" + i);
        }
        System.out.println("反射耗时   " + (System.currentTimeMillis() - now) + "ms");

        now = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            DemoTest demoTest = new DemoTest();
            Class<?> clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.DemoTest");
            Class<?>[] argsType = new Class[1];
            argsType[0] = String.class;
            Method method = clazz.getMethod("setName", argsType);
            method.invoke(demoTest, "test" + i);
        }
        System.out.println("标准反射耗时" + (System.currentTimeMillis() - now) + "ms");

//        now = System.currentTimeMillis();
//        for (int i = 0; i < total; i++) {
//            DemoTest demoTest = new DemoTest();
//            Method method = demoTest.getClass().getMethod("setName", String.class);
//            method.invoke(demoTest, "test" + i);
//        }
//        System.out.println("耗时      " + (System.currentTimeMillis() - now) + "ms");
    }

    @Test
    public void test2() throws Exception {
        long total = 500000;
        long now = System.currentTimeMillis();

        //////
        for (int i = 0; i < total; i++) {
            DemoTest demoTest = new DemoTest();
            Method method = demoTest.getClass().getMethod("setName", String.class);
            method.invoke(demoTest, "test" + i);
        }
        //////

        System.out.println("耗时" + (System.currentTimeMillis() - now) + "ms");

    }
}
