/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.biz.route.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.kdniao.common.lang.error.ApplicationException;
import com.kdniao.common.lang.result.BaseResult;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.route.RouteSubscribeInvokeManager;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeHttpSender;
import com.kdniao.logisticsfront.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author zhou.gw
 * @version $Id: RouteSubscribeInvokeManagerImpl.java, v 0.1 2018年9月17日 下午7:27:12 admin Exp $
 */
public class RouteSubscribeInvokeManagerImpl implements RouteSubscribeInvokeManager {

    private static final String ROUTE_SUBSCRIBE_BODY = "companyCode=%s&params=%s&digest=%s&timestamp=%s";
    private static final String REQUEST_PARAMS = "{\"order_number\":\"%s\",\"tracking_number\":\"%s\"}";
    private String parternKey = "7e317f77802ee219c379f2e9c47be4b0";// String.Empty;
    private String logCompanyID = "DEPPON"; //物流公司 ID
    private String merchantId = "EWBKDN";// string.Empty;
    private ExterfaceInvokeHttpSender routeSubscribeHttpSender;

    public void setParternKey(String parternKey) {
        this.parternKey = parternKey;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setRouteSubscribeHttpSender(ExterfaceInvokeHttpSender routeSubscribeHttpSender) {
        this.routeSubscribeHttpSender = routeSubscribeHttpSender;
    }

    public static void main(String[] args) throws Exception {
        ExterfaceInvokeHttpSender httpSender = new ExterfaceInvokeHttpSender();
        httpSender.setHostname("http://dpsanbox.deppon.com/sandbox-web/standard-order/standTraceSubscribe.action");
        httpSender.afterPropertiesSet();
        RouteSubscribeInvokeManagerImpl invoker = new RouteSubscribeInvokeManagerImpl();
        invoker.setRouteSubscribeHttpSender(httpSender);
        invoker.setMerchantId("EWBSZSKJSJ");
        invoker.setParternKey("dd089ff94824ce214a23bac05b76a784");
        Waybill waybill = new Waybill();
        WaybillKey waybillKey = new WaybillKey("KDN002", "YTO");
        waybill.setWaybillKey(waybillKey);
        BaseResult result = new BaseResult();
        invoker.invoke(waybill);
        System.out.println(result.getErrorContext());
    }

    /**
     *   RouteSubscribeInvokeManager#invoke(Waybill)
     */
    @Override
    public void invoke(Waybill waybill) {
        Print.out("轨迹订阅demo ", waybill.getWaybillCode());

        WaybillKey waybillKey = waybill.getWaybillKey();
//        String requestParemStr = String.format(REQUEST_PARAMS, "", waybillKey.getWaybillCode());
//        String digest = null;
//        long timestamp = System.currentTimeMillis();
//        try {
//            digest = Base64.encodeBase64String(DigestUtils.md5Hex(requestParemStr + parternKey + timestamp).getBytes());
//        } catch (Exception e) {
//            throw new ApplicationException(ApplicationErrors.systemError.message("failed on sign"), e);
//        }
//        String stringBody = String.format(ROUTE_SUBSCRIBE_BODY, merchantId, requestParemStr, digest, timestamp);
//        String responseString = routeSubscribeHttpSender.send(stringBody);
//        if (StringUtils.isEmpty(responseString)) {
//            throw new ApplicationException(ApplicationErrors.invoke3thFailed.message(responseString));
//        }
//        JSONObject parseObject = null;
//        try {
//            parseObject = JSON.parseObject(responseString);
//        } catch (JSONException je) {
//            throw new ApplicationException(ApplicationErrors.invoke3thFailed.message(responseString));
//        }
//        if (parseObject == null) {
//            throw new ApplicationException(ApplicationErrors.invoke3thFailed.message(responseString));
//        }
//        if (!parseObject.getBoolean("success")) {
//            throw new ApplicationException(ApplicationErrors.invoke3thFailed.message(responseString));
//        }
    }
}