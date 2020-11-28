//package com.spring.demo;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.WebResourceRoot;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.catalina.webresources.DirResourceSet;
//import org.apache.catalina.webresources.StandardRoot;
//
///**
// * 模拟tomcat通过main方法启动
// */
//public class ApplicationStartDemo5 {
//    public static void main(String[] args) {
//        ApplicationStartDemo5.run(ApplicationStartDemo5.class, args);
//    }
//
//    public static void run(Class<?> primarySource, String... args) {
//        String sourcePath = ApplicationStartDemo5.class.getResource("/").getPath();
//        String path = "/C:/Users/AY180/code/demo/spring-initialize/spring-initialize-5/src/main/webapp";
//
//        Tomcat tomcat = new Tomcat();
//        try {
//            System.out.println(sourcePath);
//            System.out.println(path);
//
//            // tomcat.setHostname("localhost");
//            tomcat.setPort(8080);
//            Context context = tomcat.addWebapp("/", path);
//
//            WebResourceRoot webResourceRoot = new StandardRoot(context);
//            webResourceRoot.addPreResources(new DirResourceSet(webResourceRoot, "/WEB-INF/classes", sourcePath, "/"));
//            context.setResources(webResourceRoot);
//
//            tomcat.start();
//            tomcat.getServer().await();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // http://localhost:8080/product
//    }
//}
