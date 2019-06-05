/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.route.RouteQueryRouteFacade;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteQueryOrder;
import com.kdniao.logisticsgw.common.service.result.RouteQueryResult;
import com.kdniao.merchant.common.service.model.setting.CustomerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * 
 * @author huangboke
 * @version $Id: RouteQueryRouteFacadeTest.java, v 0.1 2017年3月28日 下午1:53:44 huangboke Exp $
 */
public class RouteQueryRouteFacadeTest extends BaseTest {

    @Autowired
    private RouteQueryRouteFacade routeQueryRouteFacade;

    @Test
    public void testNormal() {
        RouteQueryOrder order = new RouteQueryOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("123456789", "EMS"));
        order.setWaybill(waybill);
        order.setPartnerId("1264635");
        order.setPartnerGroup(CustomerGroup.G998);
        RouteQueryResult result = this.routeQueryRouteFacade.route(order, false);
        System.out.println(result.isSuccess());
        if (!result.isSuccess()) {
            System.out.println(result.getErrorContext().fetchCurrentError());
        }
        System.out.println(result.getRoute());
    }

    //@Test
    public void testAsyncNormal() {
        RouteQueryOrder order = new RouteQueryOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("450680574176", "ZTO"));
        order.setWaybill(waybill);
        order.setPartnerId("1264635");
        order.setPartnerGroup(CustomerGroup.G001);
        RouteQueryResult result = this.routeQueryRouteFacade.route(order, true);
        System.out.println(result.isSuccess());
        if (!result.isSuccess()) {
            System.out.println(result.getErrorContext().fetchCurrentError());
        }
        System.out.println(result.getRoute());
    }

    public void testNormalKDN() {
        RouteQueryOrder order = new RouteQueryOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("1239182371923", "KDN"));
        order.setWaybill(waybill);
        order.setPartnerId("111111");
        order.setPartnerGroup(CustomerGroup.G001);
        RouteQueryResult result = this.routeQueryRouteFacade.route(order, false);
        System.out.println("======================TestResult======================");
        System.out.println(result.isSuccess());
        if (!result.isSuccess()) {
            System.out.println(result.getErrorContext().fetchCurrentError());
        }
        System.out.println(result.getRoute());
    }
}