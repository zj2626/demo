package com.kdniao.logisticsfront.common.biz.service.impl.classLoader;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ClassLoaderDemo {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    /**
     * ClassLoader
     * <p>
     * java.lang.ClassLoader类的基本职责就是根据一个指定的类的名称，找到或者生成其对应的字节代码，然后从这些字节代码中定义出一个Java 类，即 java.lang.Class类的一个实例
     * <p>
     * JVM中类加载器的树状层次结构:
     * 1.引导类加载器（bootstrap class loader）： 它用来加载 Java 的核心库(jre/lib/rt.jar)，是用原生C++代码来实现的，并不继承自java.lang.ClassLoader。加载扩展类和应用程序类加载器，并指定他们的父类加载器，在java中获取不到
     * 2.扩展类加载器（extensions class loader）：它用来加载 Java 的扩展库(jre/ext/*.jar)。Java 虚拟机的实现会提供一个扩展库目录。该类加载器在此目录里面查找并加载 Java 类。
     * 3.系统类加载器（system class loader）：它根据 Java 应用的类路径（CLASSPATH）来加载 Java 类。一般来说，Java 应用的类都是由它来完成加载的。可以通过 ClassLoader.getSystemClassLoader()来获取它。
     * 4.自定义类加载器（custom class loader）：除了系统提供的类加载器以外，开发人员可以通过继承 java.lang.ClassLoader类的方式实现自己的类加载器，以满足一些特殊的需求。
     * <p>
     * 双亲委派机制: 某个特定的类加载器在接到加载类的请求时，首先将加载任务委托交给父类加载器，父类加载器又将加载任务向上委托，直到最父类加载器，如果最父类加载器可以完成类加载任务，就成功返回，如果不行就向下传递委托任务，由其子类加载器进行加载。
     * 好处: 保证java核心库的安全性
     * 代理模式: 与双亲委派机制相反，代理模式是先自己尝试加载，如果无法加载则向上传递。tomcat就是代理模式。
     */
    @Test
    public void test() {
        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));

        // ClassLoader是由AppClassLoader加载的
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        //application class loader
        System.out.println(classLoader);
        //extensions class loader
        System.out.println(classLoader.getParent());
        //bootstrap class loader
        System.out.println(classLoader.getParent().getParent());

        try {
            Class clazz = classLoader.loadClass("com.kdniao.logisticsfront.common.biz.service.impl.classLoader.bean.UserA");
            Object object = clazz.newInstance();
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        CustomClassLoader classLoader = new CustomClassLoader("H:");
        // 方法一
//        Class<?> clazz = Class.forName("com.kdniao.logisticsfront.common.biz.service.impl.classLoader.bean.UserA", true, classLoader);
        // 方法二
        Class<?> clazz = classLoader.loadClass("com.kdniao.logisticsfront.common.biz.service.impl.classLoader.bean.UserA");
        System.out.println(clazz.getClassLoader());

        Object object = clazz.newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());

        System.out.println("#########################");
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        System.out.println(cl);
    }

    @Test
    public void test3() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Thread.currentThread().setContextClassLoader(new CustomClassLoader("H:"));

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz = classLoader.loadClass("com.kdniao.logisticsfront.common.biz.service.impl.classLoader.bean.UserA");
        System.out.println(clazz.getClassLoader());

        Object object = clazz.newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }
}
