package com.spring.demo;

import com.spring.demo.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 模拟tomcat通过main方法启动
 */
public class ApplicationStartDemoX {
    public static void main(String[] args) {
        ApplicationStartDemoX.run(ApplicationStartDemoX.class, args);
    }

    public static void run(Class<?> primarySource, String... args) {
        String sourcePath = ApplicationStartDemoX.class.getResource("/").getPath();
        String path = "/C:/Users/AY180/code/demo/spring-initialize/spring-initialize-x/src/main/resources";

        Tomcat tomcat = new Tomcat();
        try {
            System.out.println(sourcePath);
            System.out.println(path);

            // tomcat.setHostname("localhost");
            tomcat.setPort(8080);
            Context context = tomcat.addWebapp("/", path);
            //            tomcat.addWebapp("/", "F:/demo/spring-initialize/spring-initialize-x/src/main/resources/html/");

            WebResourceRoot webResourceRoot = new StandardRoot(context);
            webResourceRoot.addPreResources(new DirResourceSet(webResourceRoot, "/html", sourcePath, "/"));
            context.setResources(webResourceRoot);

            // initContext(tomcat);
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
        mvc.addMapping("/");
    }
}
