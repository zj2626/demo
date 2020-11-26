package com.spring.demo;

import com.spring.demo.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @see org.springframework.web.SpringServletContainerInitializer#onStartup(java.util.Set, javax.servlet.ServletContext)
 */
public class MyApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 初始化web的spring环境
        System.out.println("SPRING-WEB-INITIALIZE ...");
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(AppConfig.class);
        webApplicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("my_mvc", servlet);
        registration.addMapping("/");
        registration.setLoadOnStartup(1);
    }
}
