package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxyFactory implements InvocationHandler {
    private Object target; // 代理对象

    public Object getInstance(Object target) {
        this.target = target;

        return Proxy.newProxyInstance(
                this.target.getClass().getClassLoader(),
                this.target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");

        Object result = method.invoke(this.target, args);

        System.out.println("after");
        return result;
    }
}
