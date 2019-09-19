package com.demo.common.service.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DemoTest {
    private SimpleBean bean;

    @Before
    public void setUp() throws Exception {
        bean = new SimpleBean();
        bean.setName("haoyifen");
    }

    //直接通过Java的get方法
    @Test
    public void directGet() {
        bean.getName();
    }

    //通过高性能的ReflectAsm库进行测试，仅进行一次methodAccess获取
    @Test
    public void reflectAsmGet() {
        MethodAccess methodAccess = MethodAccess.get(SimpleBean.class);
        System.out.println(methodAccess.invoke(bean, "getName"));
    }

    //通过Java Class类自带的反射获得Method测试，仅进行一次method获取
    @Test
    public void javaReflectGet() throws Exception {
        Method getName = SimpleBean.class.getMethod("getName");
        System.out.println(getName.invoke(bean) + "\n");

        // protected方法访问
        Method show = SimpleBean.class.getDeclaredMethod("show");
        System.out.println(show.getName());
        show.invoke(bean);
        System.out.println();

        // private方法访问 需要setAccessible
        Method show2 = SimpleBean.class.getDeclaredMethod("show2");
        System.out.println(show2.getName());
        show2.setAccessible(true);
        show2.invoke(bean);
    }

    //通过Java Class类自带的反射获得Method测试， ??????????? 不能用
    @Test
    public void javaReflectGet3() throws Exception {
        Class clazz = Class.forName("com.demo.common.service.reflection.SimpleBean");
        Method getName = clazz.getMethod("getName");
        System.out.println(getName.invoke(clazz));
    }

    //使用Java自带的Property属性获取Method测试，仅进行一次method获取
    @Test
    public void propertyGet() throws Exception {
        Method method = null;
        BeanInfo beanInfo = Introspector.getBeanInfo(SimpleBean.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getName().equals("name")) {
                method = propertyDescriptor.getReadMethod();
                break;
            }
        }
        System.out.println(method.invoke(bean));
    }

    //BeanUtils的getProperty测试
    @Test
    public void beanUtilsGet() throws Exception {
        System.out.println(BeanUtils.getProperty(bean, "name"));
    }

    // JDK Proxy 动态代理 只能代理实现接口的类
    @Test
    public void javaProxy() throws Exception {
        MyProxyFactory myProxyFactory = new MyProxyFactory();
        Bean proxyObject = (Bean) myProxyFactory.getInstance(bean);
        System.out.println("结果: " + proxyObject.getName());
    }

    // Cglib 动态代理 可以代理类 高性能；
    @Test
    public void javaCglibProxy() throws Exception {
        MyCglibProxyFactory myProxyFactory = new MyCglibProxyFactory();
        Bean proxyObject = (Bean) myProxyFactory.getInstance(bean);
        System.out.println(proxyObject.getName());
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
            System.out.println("before");

            Object result = methodProxy.invoke(this.target, args);

            System.out.println("after");
            return result;
        }
    }
}