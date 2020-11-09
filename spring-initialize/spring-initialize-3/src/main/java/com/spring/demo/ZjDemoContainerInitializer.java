package com.spring.demo;

import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

        List<ZjDemoApplicationInitializer> initializers = new LinkedList<>();
        for (Class<?> waiClass : c) {
            if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) && ZjDemoApplicationInitializer.class.isAssignableFrom(waiClass)) {
                try {
                    initializers.add((ZjDemoApplicationInitializer) ReflectionUtils.accessibleConstructor(waiClass).newInstance());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }

        //   AnnotationAwareOrderComparator.sort(initializers);
        for (ZjDemoApplicationInitializer initializer : initializers) {
            initializer.onDoRunStartup(ctx);
        }

        System.out.println("ZJ FINISH > > ZjDemoContainerInitializer - onDoRunStartup \n");
    }
}
