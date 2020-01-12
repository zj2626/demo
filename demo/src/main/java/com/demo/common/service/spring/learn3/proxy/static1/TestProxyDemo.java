package com.demo.common.service.spring.learn3.proxy.static1;

import org.junit.Test;

public class TestProxyDemo {
    /*
    静态代理实现方式: 继承

    缺点: 类爆炸
     */

    @Test
    public void test(){
        ProxyClass proxy = new ProxyClass();
        proxy.action1();
    }
}
