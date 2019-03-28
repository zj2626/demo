package com.kdniao.logisticsfront.common.biz.service.impl.company;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.kdniao.logisticsgw.common.service.gateway.RouteQueryExterfaceInvoker;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteQueryInvokeOrder;
import com.kdniao.logisticsgw.common.service.result.RouteQueryInvokeResult;

public class TestFront {
    static ApplicationConfig ac = null;
    static RegistryConfig rc = null;

    static {
        try {
            ac = new ApplicationConfig();
            ac.setName("ydcrackyj");
            rc = new RegistryConfig();
            rc.setAddress("zookeeper://192.168.1.230:2181");
            rc.setProtocol("zookeeper");//协议
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T getProvider(Class<T> c, String group) {
        ReferenceConfig<T> rcc = new ReferenceConfig<T>();
        rcc.setTimeout(50000);
        rcc.setRegistry(rc);
        rcc.setApplication(ac);
        rcc.setCheck(false);
        rcc.setInterface(c);
        rcc.setGroup(group);
        return rcc.get();
    }

    public static void test() {
        RouteQueryExterfaceInvoker invoker = getProvider(RouteQueryExterfaceInvoker.class, "logisticsfront-test.YD-B");
        RouteQueryInvokeOrder order = new RouteQueryInvokeOrder();
        WaybillKey waybillKey = new WaybillKey("6000046239373", "YD");
        order.setWaybillKey(waybillKey);
        System.out.println("#################################");
        RouteQueryInvokeResult result = invoker.invoke(order);
        System.out.println("#########");
        System.out.println(JSON.toJSONString(result));
    }

    public static void main(String[] args) {
        test();
    }
}
