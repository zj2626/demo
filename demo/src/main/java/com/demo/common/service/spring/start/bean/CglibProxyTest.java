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

        /**
         * MethodInterceptor 的方法 intercept() 中两个参数 Method method, MethodProxy proxy, 如果被代理方法是同一个方法调用的则method就是被代理方法,
         * 如果是不同的方法则method是调用方的method对象
         *
         * @param o           代理对象
         * @param method      被代理方法
         * @param args        方法参数
         * @param methodProxy 代理方法
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object result = null;

            System.out.println("before 前置通知");
            try {
                result = methodProxy.invoke(this.target, args);
                //                result = methodProxy.invokeSuper(this.target, args); // TODO ??????
            } catch (Exception e) {
                System.out.println("error 异常通知");
            } finally {
                System.out.println("after 后置通知");
            }
            return result;
        }
    }
}