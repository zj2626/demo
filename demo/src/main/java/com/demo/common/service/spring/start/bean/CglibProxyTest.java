package com.demo.common.service.spring.start.bean;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyTest {
    private BeanInterface bean;

    @Before
    public void setUp() throws Exception {
        bean = new MyBean();
        bean.setName("FFFFffffFFFF");
    }

    // Cglib 动态代理 可以代理类 高性能；
    @Test
    public void javaCglibProxy() throws Exception {
        MyCglibProxyFactory myProxyFactory = new MyCglibProxyFactory();
        MyBean proxyObject = (MyBean) myProxyFactory.getInstance(bean);
        System.out.println(proxyObject.getName());
    }

    class MyCglibProxyFactory implements MethodInterceptor {
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
            Object result = null;

            System.out.println("before 前置通知");
            try {
                result = methodProxy.invoke(this.target, args);
            } catch (Exception e) {
                System.out.println("error 异常通知");
            } finally {
                System.out.println("after 后置通知");
            }
            return result;
        }
    }
}