package com.demo.common.service.design.structural.proxy.demo1;

import org.junit.Test;

/**
 * 静态代理: 需要定义接口或者父类,被代理对象与代理对象一起实现相同的接口或者是继承相同父类
 */
public class StructDemoTest {

    @Test
    public void test1() {
        Subject realSubject= new RealSubject();

        ProxyFactory proxy = new ProxyFactory(realSubject);
        proxy.request("sex !");
    }

}
