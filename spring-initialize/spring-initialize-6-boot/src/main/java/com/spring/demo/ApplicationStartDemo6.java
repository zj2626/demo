package com.spring.demo;

import com.spring.demo.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;
import java.io.File;

/**
 * 模拟tomcat通过main方法启动
 */
public class ApplicationStartDemo6 {
    public static void main(String[] args) {
        ApplicationStartDemo6.run(ApplicationStartDemo6.class, args);
    }

    public static void run(Class<?> primarySource, String... args) {
        // 初始化web的spring环境
        System.out.println("SPRING-WEB-INITIALIZE ...");
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(AppConfig.class);
        webApplicationContext.refresh();

        File base = new File(System.getProperty("java.io.tmpdir"));

        Tomcat tomcat = new Tomcat();
        try {
            System.out.println(base.getAbsolutePath());

            // tomcat.setHostname("localhost");
            tomcat.setPort(8080);
            Context context = tomcat.addContext("/", base.getAbsolutePath());

            // Create and register the DispatcherServlet
            DispatcherServlet servlet = new DispatcherServlet(webApplicationContext);
            Tomcat.addServlet(context,"my_mvc", servlet).setLoadOnStartup(1);
            context.addServletMapping("/", "my_mvc");

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // http://localhost:8080/product
    }
}
