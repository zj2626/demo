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
 * <p>
 * 在容器启动的时候会调用到SpringServletContainerInitializer#onStartup方法
 * 其会通过注解@HandlesTypes({WebApplicationInitializer.class})把所有的WebApplicationInitializer实现都赋值到onStartup的参数Set<>webAppInitializerClasses中
 * SpringServletContainerInitializer#onStartup方法是通过SPI机制: 当启动web容器的时候，会自动到添加的相应jar包下找到META-INF/services下以ServletContainerInitializer的全路径名称命名的文件，它的内容为ServletContainerInitializer实现类的全路径，将它们实例化
 */
public class MyApplicationInitializer implements WebApplicationInitializer {
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
        webApplicationContext.register(AppConfig.class);
        //        webApplicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("my_mvc", servlet);
        registration.addMapping("/");
        registration.setLoadOnStartup(1);
    }
}
