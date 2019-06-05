package com.kdniao.logisticsfront.common.biz.service.impl.logisticsfront.common.biz.service.impl.company;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.fastjson.JSON;
import com.kdniao.logisticsgw.common.service.facade.route.RouteQueryRouteFacade;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteQueryOrder;
import com.kdniao.logisticsgw.common.service.result.RouteQueryResult;
import com.kdniao.merchant.common.service.model.setting.CustomerGroup;

public class TestGW {
    static ApplicationConfig ac = null;
    static RegistryConfig rc = null;

    static {
        try {
            ac = new ApplicationConfig();
            ac.setName("logisticsgw-test");
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
        RouteQueryRouteFacade invoker = getProvider(RouteQueryRouteFacade.class, "logisticsgw-test");
        RouteQueryOrder order = new RouteQueryOrder();
        WaybillKey waybillKey = new WaybillKey("9374869903503227645239", "DHL_GLB");
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(waybillKey);
        waybill.setCustomerName("");
        order.setWaybill(waybill);
        order.setPartnerId("1261885");
        order.setPartnerGroup(CustomerGroup.G998);
        System.out.println("#################################");
        RouteQueryResult result = invoker.route(order, false);
        System.out.println("#########");
        System.out.println(JSON.toJSONString(result));
    }

    public static void main(String[] args) {
        test();
    }
}
