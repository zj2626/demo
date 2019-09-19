package com.demo.common.service.design.structural.proxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    // way 1
    public Object getProxyInstance(Object[] parameters) {
        // 创建动态代理类实例对象 获取被代理的对象信息
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        beforeRequest();

                        Object result = method.invoke(target, parameters);

                        afterRequest();

                        return result;
                    }
                }
        );
    }

    private void beforeRequest() {
        System.out.println("beforeRequest");
    }

    private void afterRequest() {
        System.out.println("afterRequest");
    }

}
