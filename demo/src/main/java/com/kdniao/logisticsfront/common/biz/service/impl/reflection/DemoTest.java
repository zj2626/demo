package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

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
        System.out.println(getName.invoke(bean));
    }

    //通过Java Class类自带的反射获得Method测试， ??????????? 不能用
    @Test
    public void javaReflectGet2() throws Exception {
        Class clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.SimpleBean");
        Object object = clazz.newInstance();
        Method getName = clazz.getMethod("getName");
        System.out.println(getName.invoke(object));
    }

    //通过Java Class类自带的反射获得Method测试， ??????????? 不能用
    @Test
    public void javaReflectGet3() throws Exception {
        Class clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.reflection.SimpleBean");
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
        System.out.println(proxyObject.getName());
    }

    // Cglib 动态代理 可以代理类 高性能；
    @Test
    public void javaCglibProxy() throws Exception {
        MyCglibProxyFactory myProxyFactory = new MyCglibProxyFactory();
        Bean proxyObject = (Bean) myProxyFactory.getInstance(bean);
        System.out.println(proxyObject.getName());
    }
}