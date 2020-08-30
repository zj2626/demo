package com.spring.demo;

import com.spring.demo.config.AppConfig;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 模拟tomcat通过main方法启动
 */
public class ApplicationStartDemo {
    public static void main(String[] args) {
        ApplicationStartDemo.run(ApplicationStartDemo.class, args);
    }

    public static void run(Class<?> primarySource, String... args) {
        Tomcat tomcat = new Tomcat();
        try {
            // tomcat.setHostname("localhost");
            tomcat.setPort(8080);
//            tomcat.addWebapp("/", "C:/Users/AY180/code/demo/spring-initialize/src/main/resources/html/");
            tomcat.addWebapp("/", "F:/demo/spring-initialize/src/main/resources/html/");

            //            initContext(tomcat);
            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initContext(Tomcat tomcat) {
        System.out.println("TOMCAT-SPRING-WEB-INITIALIZE ...");
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(AppConfig.class);
        //        webApplicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
        Wrapper mvc = tomcat.addServlet("/", "mvc", servlet);
        mvc.setLoadOnStartup(1);
        mvc.addMapping("/*");
    }
}
