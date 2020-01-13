package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;

import java.lang.reflect.Method;

public class MyProxyInvocationHandler implements ProxyInvocationHandler {
    private TargetInterface target;

    public MyProxyInvocationHandler(TargetInterface target) {
        this.target = target;
    }

    @Override
    public Object invoke(Method method, Object[] args) throws Throwable {
        System.out.println("log");
        return method.invoke(target, args);
    }
}