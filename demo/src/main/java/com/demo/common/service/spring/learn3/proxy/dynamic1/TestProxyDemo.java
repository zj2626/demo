package com.demo.common.service.spring.learn3.proxy.dynamic1;

import com.demo.common.service.spring.learn3.proxy.dynamic1.bean.Person;
import org.junit.Test;

public class TestProxyDemo {
    /*
    动态代理实现
     */

    @Test
    public void test(){
        TargetInterface target = new TargetClass();
        TargetInterface proxy = (TargetInterface) ProxyUtil.newInstance(target);
        proxy.action2("this is fucking message", 999, new Person("bitch"));
        System.out.println(proxy.action1());
    }
}
