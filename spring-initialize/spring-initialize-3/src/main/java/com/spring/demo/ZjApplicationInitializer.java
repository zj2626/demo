package com.spring.demo;

import com.spring.demo.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @see org.springframework.web.SpringServletContainerInitializer#onStartup(java.util.Set, ServletContext)
 * <p>
 *
 *     模拟com.spring.demo.MyApplicationInitializer执行原理
 */
public class ZjApplicationInitializer implements ZjDemoApplicationInitializer {
    @Override
    public void onDoRunStartup(ServletContext servletContext) throws ServletException {
        System.out.println("ZJ SPRING-WEB-INITIALIZE ...");
    }
}
