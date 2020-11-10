package com.spring.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
