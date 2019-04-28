package com.kdniao.logisticsfront.common.biz.service.impl.thread.mbean;


import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class HelloWorldAgent {
    public static void main(String[] args) throws Exception {
        // 首先建立一个MBeanServer，MBeanServer用来管理我们的MBean，通常是通过MBeanServer来获取我们MBean的信息
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        String domainName = "MyMBean";
        // 为MBean（下面的new Hello()）创建ObjectName实例
        ObjectName helloName = new ObjectName(domainName + ":name=HelloWorld");

        // 将new Hello()这个对象注册到MBeanServer上去
        server.registerMBean(new DemoClass(), helloName);

    }
}
