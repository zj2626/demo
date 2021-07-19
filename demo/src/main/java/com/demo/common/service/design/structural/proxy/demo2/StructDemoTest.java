package com.demo.common.service.design.structural.proxy.demo2;

import org.junit.Test;

/**
 * 动态代理
 */
public class StructDemoTest {

    // 不好用
    @Test
    public void test1() {
        Subject realSubject = new RealSubject();

        Subject proxySubject = (Subject) new ProxyFactory(realSubject).getProxyInstance();

        System.out.println(realSubject.getClass());
        System.out.println(proxySubject.getClass());
        System.out.println();

        proxySubject.request("sex !");
        proxySubject.request2("sex2 !", "sex3 -|");
        proxySubject.request3("sex3 !");

        // proxySubject.fatherSMethod();
        System.out.println("是否相等: " + (realSubject == proxySubject));
    }
}
