package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory2 implements InvocationHandler {
    private Object target;

    public ProxyFactory2(Object target) {
        this.target = target;
    }

    // way 2
    public Object getProxyInstance() {
        // 创建动态代理类实例对象 获取被代理的对象信息
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Exception {
        beforeRequest();

        Object result = method.invoke(target, parameters);

        afterRequest();

        return result;
    }

    private void beforeRequest() {
        System.out.println("beforeRequest");
    }

    private void afterRequest() {
        System.out.println("afterRequest");
    }

}
