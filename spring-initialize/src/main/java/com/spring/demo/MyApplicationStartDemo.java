package com.spring.demo;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * 模拟tomcat通过main方法启动
 */
public class MyApplicationStartDemo {
    public static void main(String[] args) {
        MyApplicationStartDemo.run(MyApplicationStartDemo.class, args);
    }

    public static void run(Class<?> primarySource, String... args) {
        Tomcat tomcat = new Tomcat();
        try {
            tomcat.setPort(8080);
            tomcat.addWebapp("/", "C:/Users/AY180/code/demo/spring-initialize/src/main/resources/html/");

                tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
