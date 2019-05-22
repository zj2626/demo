package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2;

import org.junit.Test;

/**
 * 动态代理
 */
public class StructDemoTest {

    // 不好用
    @Test
    public void test1() {
        Subject realSubject = new RealSubject();

        Subject proxySubject = (Subject) new ProxyFactory(realSubject).getProxyInstance(new Object[]{"wtffff"});

        System.out.println(proxySubject.getClass() + "*******\n");

        proxySubject.request("sex !");
        proxySubject.request2("sex2 !", "sex3 -|");
    }

    @Test
    public void test2() {
        Subject realSubject = new RealSubject();

        Subject proxySubject = (Subject) new ProxyFactory2(realSubject).getProxyInstance();

        System.out.println(proxySubject.getClass() + "*******\n");

        proxySubject.request("sex !");
        proxySubject.request2("sex2 !", "sex3 -|");
    }
}
