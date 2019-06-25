package com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {
    private BeanInterface bean;

    @Before
    public void setUp() throws Exception {
        bean = new MyBean();
        bean.setName("FFFFffffFFFF");
    }

//    @Test
//    public void javaReflectGet() throws Exception {
//        Method getName = MyBean.class.getMethod("getName");
//        System.out.println(getName.invoke(bean) + "\n");
//
//        // protected方法访问
//        Method show = MyBean.class.getDeclaredMethod("show");
//        System.out.println(show.getName());
//        show.invoke(bean);
//        System.out.println();
//
//        // private方法访问 需要setAccessible
//        Method show2 = MyBean.class.getDeclaredMethod("show2");
//        System.out.println(show2.getName());
//        show2.setAccessible(true);
//        show2.invoke(bean);
//    }

    // JDK Proxy 动态代理 只能代理实现接口的类
    @Test
    public void ProxyTest() throws Exception {
        MyProxyFactory myProxyFactory = new MyProxyFactory();
        BeanInterface proxyObject = (BeanInterface) myProxyFactory.getInstance(bean);
        System.out.println("结果: " + proxyObject.getName());
    }

    class MyProxyFactory implements InvocationHandler {
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
            Object result = null;

            System.out.println("before 前置通知");
            try {
                result = method.invoke(this.target, args);
            } catch (Exception e) {
                System.out.println("error 异常通知");
            } finally {
                System.out.println("after 后置通知");
            }

            return result;
        }
    }
}