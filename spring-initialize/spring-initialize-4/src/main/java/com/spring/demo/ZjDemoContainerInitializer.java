package com.spring.demo;

import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 模拟 org.springframework.web.SpringServletContainerInitializer实现过程
 */
@HandlesTypes(ZjDemoApplicationInitializer.class)
public class ZjDemoContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("\nZJ BEGIN > > ZjDemoContainerInitializer - onDoRunStartup ");
        System.out.println(c.size());
        System.out.println(c);

        ServletRegistration.Dynamic registration = ctx.addServlet("my_zj_mvc", new ZjServlet());
        // 拦截所有请求
        registration.addMapping("/");

        System.out.println("ZJ FINISH > > ZjDemoContainerInitializer - onDoRunStartup \n");
    }
}
