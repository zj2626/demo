package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;

import java.lang.reflect.Method;

public interface ProxyInvocationHandler {
    Object invoke(Method method, Object[] args) throws Throwable;
}
