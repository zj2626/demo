package com.kdniao.logisticsfront.common.biz.service.impl.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class StructDemoTest {

    @Test
    public void test1() {
        // 目标对象
        Subject realSubject = new RealSubject();

        Subject proxySubject = (Subject) new ProxyFactory(realSubject).getProxyInstance();

        System.out.println(proxySubject.getClass() + "*******\n");

        proxySubject.requestA("sex !");
        proxySubject.requestB("sex2 !", "sex3 -|");
        proxySubject.fatherSMethod();
    }

    @Test
    public void test2() {
        Subject realSubject = new RealSubject();

        Subject proxySubject = (Subject) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                new ProxyFactory(realSubject)
        );

        System.out.println(proxySubject.getClass() + "*******\n");
        proxySubject.requestA("sex !");
    }

//    @Test
//    public void test3() {
//        Parent parent = new RealSubject();
//
//        Parent proxySubject = (Parent) Proxy.newProxyInstance(
//                parent.getClass().getClassLoader(),
//                parent.getClass().getInterfaces(),
//                new ProxyFactory(parent)
//        );
//
//        System.out.println(proxySubject.getClass() + "*******\n");
//        proxySubject.cannotUse();
//    }
}
