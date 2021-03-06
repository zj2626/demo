package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;

import com.demo.common.service.spring.learn3.proxy.dynamic.jdk.bean.Person;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxyDemo {
    /*
    动态代理实现
     */

    @Test
    public void test() {
        TargetInterface target = new TargetClass();
        TargetInterface proxy = (TargetInterface) ProxyUtil.newInstance(target);
        proxy.action2("this is fucking message", 999, new Person("bitch"));
        System.out.println(proxy.action1());
    }

    @Test
    public void testJDK() {
        TargetInterface target = new TargetClass();

        TargetInterface proxy = (TargetInterface) Proxy.newProxyInstance(TargetInterface.class.getClassLoader(), new Class[]{TargetInterface.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("log");
                        return method.invoke(target, args);
                    }
                });
        proxy.action2("this is fucking message", 999, new Person("bitch"));
        System.out.println(proxy.action1());
    }

    /**
     * 根据JDK动态代理进行优化
     */
    @Test
    public void test2() {
        TargetInterface target = new TargetClass();
        TargetInterface proxy = (TargetInterface) ProxyUtil2.newInstance(TargetInterface.class, new MyProxyInvocationHandler(target));
//        TargetInterface proxy = new Proxy$01(new MyProxyInvocationHandler(target));
        proxy.action2("this is fucking message", 999, new Person("bitch"));
        System.out.println(proxy.action1());
    }
}
