package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;
import com.demo.common.service.spring.learn3.proxy.dynamic.jdk.TargetInterface;

public class Proxy$01 implements TargetInterface {
    private com.demo.common.service.spring.learn3.proxy.dynamic.jdk.MyProxyInvocationHandler handler;
    public Proxy$01 (com.demo.common.service.spring.learn3.proxy.dynamic.jdk.MyProxyInvocationHandler handler){
        this.handler = handler;
    }

    @Override
    public void action2(java.lang.String v0,java.lang.Integer v1,com.demo.common.service.spring.learn3.proxy.dynamic.jdk.bean.Person v2){
        try {
            Object[] args = new Object[]{ v0, v1, v2};
            Class[] paraClassess = new Class[]{java.lang.String.class,java.lang.Integer.class,com.demo.common.service.spring.learn3.proxy.dynamic.jdk.bean.Person.class};
            java.lang.reflect.Method method = Class.forName("com.demo.common.service.spring.learn3.proxy.dynamic.jdk.TargetInterface").getDeclaredMethod("action2", paraClassess);
            handler.invoke(method, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public java.lang.String action1(){
        try {
            Object[] args = new Object[]{};
            Class[] paraClassess = new Class[]{};
            java.lang.reflect.Method method = Class.forName("com.demo.common.service.spring.learn3.proxy.dynamic.jdk.TargetInterface").getDeclaredMethod("action1", paraClassess);
            return (java.lang.String)handler.invoke(method, args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
