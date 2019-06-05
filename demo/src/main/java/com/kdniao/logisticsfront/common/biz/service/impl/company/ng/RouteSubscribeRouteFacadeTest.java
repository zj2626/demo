/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.lang.result.BaseResult;
import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.route.RouteSubscribeRouteFacade;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteSubscribeOrder;
import com.kdniao.merchant.common.service.model.setting.CustomerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * 
 * @author huangboke
 * @version $Id: RouteSubscribeRouteFacadeTest.java, v 0.1 2017年3月28日 下午1:53:44 huangboke Exp $
 */
public class RouteSubscribeRouteFacadeTest extends BaseTest {

    @Autowired
    private RouteSubscribeRouteFacade routeSubscribeRouteFacade;

    @Test
    public void testNormal() {
        RouteSubscribeOrder order = new RouteSubscribeOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("sllllll33", "YTO"));
        order.setWaybill(waybill);
        order.setPartnerId("1264635");
        order.setPartnerGroup(CustomerGroup.G001);
        BaseResult result = this.routeSubscribeRouteFacade.route(order);
        System.out.println(result.isSuccess());
        if (!result.isSuccess()) {
            System.out.println(result.getErrorContext().fetchCurrentError());
        }
    }
}