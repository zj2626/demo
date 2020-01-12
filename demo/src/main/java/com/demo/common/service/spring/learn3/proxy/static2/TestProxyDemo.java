package com.demo.common.service.spring.learn3.proxy.static2;

import org.junit.Test;

public class TestProxyDemo {
    /*
    静态代理实现方式: 聚合

    缺点: 类爆炸, 只能代理指定对象
     */

    @Test
    public void test(){
        TargetInterface target = new TargetClass();
        ProxyClass proxy = new ProxyClass(target);
        proxy.action1();
    }

    @Test
    public void test2(){
        TargetInterface target = new TargetClass();
        ProxyClass proxy0 = new ProxyClass(target);
        ProxyClass proxy = new ProxyClass(proxy0);
        proxy.action1();
    }
}
