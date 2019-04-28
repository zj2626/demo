package com.kdniao.logisticsfront.common.biz.service.impl.thread.mbean;

import org.junit.Test;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;

public class MbeanDemo {

    @Test
    public void test1() {
        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        System.out.println(mBeanServer);
    }

    @Test
    public void test2() {
        MBeanServer mBeanServer = MBeanServerFactory.newMBeanServer();
        System.out.println(mBeanServer);
    }

    @Test
    public void test3() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        System.out.println(mBeanServer);

        Hashtable<String, String> counterProps = new Hashtable<String, String>();
        counterProps.put("type", "w-t-f");
        ObjectName on = null;
        try {
            on = new ObjectName("com.zj", counterProps);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        try {
            mBeanServer.registerMBean(new Object(), on);
        } catch (JMException e) {
            e.printStackTrace();
        }
    }
}
