package com.spring.demo;

import com.spring.demo.config.SpringConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @see org.springframework.web.SpringServletContainerInitializer#onStartup(java.util.Set, javax.servlet.ServletContext)
 *
 * 在容器启动的时候会调用到SpringServletContainerInitializer#onStartup方法
 * 其会通过注解@HandlesTypes({WebApplicationInitializer.class})把所有的WebApplicationInitializer实现都赋值到onStartup的参数Set<>webAppInitializerClasses中
 */
public class MyApplicationInitializeDemo implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 初始化非web的spring环境
//        System.out.println("SPRING-INITIALIZE ...");
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(SpringConfig.class);
//        applicationContext.refresh();

        // 初始化web的spring环境
        System.out.println("SPRING-WEB-INITIALIZE ...");
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(SpringConfig.class);
        webApplicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
//        registration.addMapping("/app/*");
    }
}
