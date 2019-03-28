/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.biz.route.impl;

import com.kdniao.common.lang.result.BaseResult;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.route.RouteSubscribeInvokeManager;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeHttpSender;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;

/**
 * @author zhou.gw
 * @version $Id: RouteSubscribeInvokeManagerImpl.java, v 0.1 2018年9月17日 下午7:27:12 admin Exp $
 */
public class RouteSubscribeInvokeManagerImpl implements RouteSubscribeInvokeManager {

    private ExterfaceInvokeHttpSender routeSubscribeHttpSender;

    public void setRouteSubscribeHttpSender(ExterfaceInvokeHttpSender routeSubscribeHttpSender) {
        this.routeSubscribeHttpSender = routeSubscribeHttpSender;
    }

    public static void main(String[] args) throws Exception {
        ExterfaceInvokeHttpSender httpSender = new ExterfaceInvokeHttpSender();
        httpSender.setHostname("http://dpsanbox.deppon.com/sandbox-web/standard-order/standTraceSubscribe.action");
        httpSender.afterPropertiesSet();
        RouteSubscribeInvokeManagerImpl invoker = new RouteSubscribeInvokeManagerImpl();
        invoker.setRouteSubscribeHttpSender(httpSender);
        Waybill waybill = new Waybill();
        WaybillKey waybillKey = new WaybillKey("KDN002", "YTO");
        waybill.setWaybillKey(waybillKey);
        BaseResult result = new BaseResult();
        invoker.invoke(waybill);
        System.out.println(result.getErrorContext());
    }

    @Override
    public void invoke(Waybill waybill) {
        Print.out("轨迹订阅demo ", waybill.getWaybillCode());

        WaybillKey waybillKey = waybill.getWaybillKey();
    }
}