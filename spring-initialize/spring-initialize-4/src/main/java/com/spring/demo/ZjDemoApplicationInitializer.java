package com.spring.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public interface ZjDemoApplicationInitializer {
    void onDoRunStartup(ServletContext servletContext) throws ServletException;
}
