package com.demo.common.service.design.behavioral.observer;

import java.util.Properties;

// 从配置文件中读取观察者有哪些
public class PropertyMgr {
    private static Properties props = new Properties();

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Observers.properties"));

//            props.load(PropertyMgr.class.getResourceAsStream("/Observers.properties"));

//            props.load(PropertyMgr.class.getResourceAsStream("/Observers.properties"));
//            props.load(StructDemoTest.class.getResourceAsStream("/Observers.properties"));

//            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("Observers.properties"));
//            props.load(StructDemoTest.class.getClassLoader().getResourceAsStream("Observers.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProps(String key) {
        return props.getProperty(key);
    }
}
