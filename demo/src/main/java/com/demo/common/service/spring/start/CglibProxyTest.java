package com.demo.common.service.spring.start;

import com.demo.common.service.spring.start.bean.MyBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest {
    // Cglib 动态代理 可以代理类 高性能；
    @Test
    public void javaCglibProxy() throws Exception {
        MyCglibProxyFactory myProxyFactory = new MyCglibProxyFactory();
        MyBean proxyObject = (MyBean) myProxyFactory.getInstance(MyBean.class);
        System.out.println(proxyObject.toString());
        System.out.println(proxyObject.getName());
    }

    class MyCglibProxyFactory implements MethodInterceptor {

        public Object getInstance(Class<?> targetClass) {

            Enhancer enhancer = new Enhancer();
            // 设置父类为实例类
            enhancer.setSuperclass(targetClass);
            // 回调方法
            enhancer.setCallback(this);
            // 创建代理对象
            return enhancer.create();
        }

        /**
         * MethodInterceptor 的方法 intercept() 中两个参数 Method method, MethodProxy proxy, 如果被代理方法是同一个方法调用的则method就是被代理方法,
         * 如果是不同的方法则method是调用方的method对象
         *
         * @param obj         生成的代理对象
         * @param method      被代理方法信息
         * @param args        方法参数信息
         * @param methodProxy 调用方代理方法
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object result = null;

            System.out.println("\n> before 前置通知 " + method.getName());
            try {
                result = methodProxy.invokeSuper(obj, args);
            } catch (Exception e) {
                System.out.println("> error  异常通知 " + method.getName());
            } finally {
                System.out.println("> after  后置通知 " + method.getName());
            }
            return result;
        }
    }
}