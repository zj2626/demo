package com.kdniao.logisticsfront.demo.biz.delivery.impl;

import com.alibaba.fastjson.JSON;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.delivery.OrderQueryInvokeManager;
import com.kdniao.logisticsfront.biz.shared.delivery.impl.AbstractOnlineOrderManagerImpl;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeIOHttpSender;
import com.kdniao.logisticsfront.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsgw.common.service.model.order.ExpOrder;
import com.kdniao.logisticsgw.common.service.model.order.Order;
import com.kdniao.logisticsgw.common.service.order.OnlineOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OnlineOrderInvokeManagerImpl extends AbstractOnlineOrderManagerImpl {
    private static final Logger logger = LoggerFactory.getLogger(OnlineOrderInvokeManagerImpl.class);
    private OrderQueryInvokeManager orderQueryInvokeManager;
    private ExterfaceInvokeIOHttpSender onlineOrderIOHttpSender;

    public void setOrderQueryInvokeManager(OrderQueryInvokeManager orderQueryInvokeManager) {
        this.orderQueryInvokeManager = orderQueryInvokeManager;
    }

    public void setOnlineOrderIOHttpSender(ExterfaceInvokeIOHttpSender onlineOrderIOHttpSender) {
        this.onlineOrderIOHttpSender = onlineOrderIOHttpSender;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ExpOrder doInvoke(OnlineOrder order) {
        Print.out("预约取件demo ", order.getOrderCode());

        ExpOrder expOrder = new ExpOrder();
        expOrder.setSuccess(false);
        expOrder.setResultCode(ApplicationErrors.unknownError.getErrorCode());
        expOrder.setReason(ApplicationErrors.unknownError.getErrorDesc());
        expOrder.setOrderCode(order.getKdnOrderCode());
        if (order.getRepeatOrder()) {
            Order orderData = new Order();
            orderData.setOrderCode(order.getKdnOrderCode());
            Order orderResult = orderQueryInvokeManager.invoke(orderData);
            if (orderResult.isSuccess()) {
                expOrder.setLogisticCode(orderResult.getLogisticCode());
                expOrder.setSuccess(true);
                expOrder.setResultCode("100");
                expOrder.setReason("成功");
                return expOrder;
            }
        }

        System.out.println(JSON.toJSONString(order));

        expOrder.setSuccess(true);
        expOrder.setResultCode("100");
        expOrder.setOrderCode(order.getOrderCode());
        expOrder.setKdnOrderCode(order.getKdnOrderCode());
        expOrder.setReason("成功");
        expOrder.setLogisticCode(StringUtils.isEmpty(order.getLogisticCode())
                ? new SimpleDateFormat("yyyyMMdd").format(new Date()) + getFixLenthString(4)
                : order.getLogisticCode());

        return expOrder;
    }


    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }
}