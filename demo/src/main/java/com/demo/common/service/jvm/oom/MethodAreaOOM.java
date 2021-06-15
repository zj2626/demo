package com.demo.common.service.jvm.oom;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zj2626
 * @name HeapOOM
 * @description 方法区溢出
 * @create 2021-06-01 22:01
 **/
public class MethodAreaOOM {

    /**
     * vm args: -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
     */
    @Test
    public void main() throws InterruptedException {
        int i=0;
        for (; i<50000; i++) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject {

    }
}
