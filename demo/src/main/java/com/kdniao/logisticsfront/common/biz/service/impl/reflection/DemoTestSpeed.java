package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.base.Stopwatch;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class DemoTestSpeed {
    private long times = 100_000_000L;
    private SimpleBean bean;
    private String formatter = "%s %d times using %d ms";

    @Before
    public void setUp() throws Exception {
        System.out.println("Before");
        bean = new SimpleBean();
        bean.setName("haoyifen");
    }

    //直接通过Java的get方法
    @Test
    public void directGet() {
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            bean.getName();
        }
        watch.stop();
        String result = String.format(formatter, "directGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    //通过高性能的ReflectAsm库进行测试，仅进行一次methodAccess获取
    @Test
    public void reflectAsmGet() {
        MethodAccess methodAccess = MethodAccess.get(SimpleBean.class);
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            methodAccess.invoke(bean, "getName");
        }
        watch.stop();
        String result = String.format(formatter, "reflectAsmGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    //通过Java Class类自带的反射获得Method测试，仅进行一次method获取
    @Test
    public void javaReflectGet() throws Exception {
        Method getName = SimpleBean.class.getMethod("getName");
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            getName.invoke(bean);
        }
        watch.stop();
        String result = String.format(formatter, "javaReflectGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
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
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {

            method.invoke(bean);
        }
        watch.stop();
        String result = String.format(formatter, "propertyGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }

    //BeanUtils的getProperty测试
    @Test
    public void beanUtilsGet() throws Exception {
        Stopwatch watch = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            BeanUtils.getProperty(bean, "name");
        }
        watch.stop();
        String result = String.format(formatter, "beanUtilsGet", times, watch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println(result);
    }
}