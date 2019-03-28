package com.kdniao.logisticsfront.common.biz.service.impl.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Iterator;
import java.util.Set;

public class JMXClient {
    public static void main(String[] args) throws Exception {
        //connect JMX
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        String domainName = "AY-2626-";
        ObjectName mbeanName = new ObjectName(domainName + ":name=hello-world");

        //print domains
        System.out.println("Domains:---------------");
        String domains[] = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            System.out.println("Domain[" + i + "] = " + domains[i]);
        }
        System.out.println();

        //MBean count
        System.out.println("MBean count:---------------");
        System.out.println("MBean count = " + mbsc.getMBeanCount());
        System.out.println();

        //process attribute
        System.out.println("process attribute:---------------");
        mbsc.setAttribute(mbeanName, new Attribute("Name", "zj2626")); //set value
        System.out.println("Name = " + mbsc.getAttribute(mbeanName, "Name")); //get value
        System.out.println();

        //invoke via proxy
        System.out.println("invoke via proxy:---------------");
        HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, HelloMBean.class, false);
        System.out.println(proxy.printHello());
        System.out.println(proxy.printHello("zhangsan26"));
        System.out.println();

        //invoke via rmi
        System.out.println("invoke via rmi:---------------");
        System.out.println(mbsc.invoke(mbeanName, "printHello", null, null));
        System.out.println(mbsc.invoke(mbeanName, "printHello", new Object[]{"lisi"}, new String[]{String.class.getName()}));
        System.out.println();

        //get mbean information
        System.out.println("get mbean information:---------------");
        MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
        System.out.println("Hello Class:" + info.getClassName());
        System.out.println("Hello Attribute:" + info.getAttributes()[0].getName());
        System.out.println("Hello Operation:" + info.getOperations()[0].getName());
        System.out.println();

        //ObjectName of MBean
        System.out.println("ObjectName of MBean:---------------");
        Set set = mbsc.queryMBeans(null, null);
        for (Iterator it = set.iterator(); it.hasNext(); ) {
            ObjectInstance oi = (ObjectInstance) it.next();
            System.out.println(oi.getObjectName());
        }

        jmxc.close();
    }
}