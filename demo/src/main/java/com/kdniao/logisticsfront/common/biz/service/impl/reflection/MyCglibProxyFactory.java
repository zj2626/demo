package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglibProxyFactory implements MethodInterceptor {
    private Object target; // 代理对象

    public Object getInstance(Object target) {
        this.target = target;

        Enhancer enhancer = new Enhancer();
        // 设置父类为实例类
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");

        Object result = methodProxy.invoke(this.target, args);

        System.out.println("after");
        return result;
    }
}
