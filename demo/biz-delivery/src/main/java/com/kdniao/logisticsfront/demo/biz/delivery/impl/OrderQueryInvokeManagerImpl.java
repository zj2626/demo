package com.kdniao.logisticsfront.demo.biz.delivery.impl;

import com.alibaba.fastjson.JSON;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.delivery.impl.AbstractOrderQueryManagerImpl;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeIOHttpSender;
import com.kdniao.logisticsgw.common.service.model.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderQueryInvokeManagerImpl extends AbstractOrderQueryManagerImpl {
    private static final Logger logger = LoggerFactory.getLogger(OrderQueryInvokeManagerImpl.class);
    private ExterfaceInvokeIOHttpSender orderQueryIOHttpSender;

    public void setOrderQueryIOHttpSender(ExterfaceInvokeIOHttpSender orderQueryIOHttpSender) {
        this.orderQueryIOHttpSender = orderQueryIOHttpSender;
    }


    @SuppressWarnings("unchecked")
    @Override
    protected Order doInvoke(Order order) {
        Print.out("订单查询demo ", order.getOrderCode());

        Order orderResult = new Order();
        orderResult.setSuccess(false);
        orderResult.setResultCode("105");
        orderResult.setReason("查询异常");

        System.out.println(JSON.toJSONString(order));

        orderResult.setSuccess(true);
        orderResult.setResultCode("100");
        orderResult.setOrderCode(order.getOrderCode());
        orderResult.setReason("查到喽 ffffffff");
        orderResult.setLogisticCode(org.apache.commons.lang3.StringUtils.isEmpty(order.getLogisticCode())
                ? new SimpleDateFormat("yyyyMMdd").format(new Date()) + getFixLenthString(4)
                : order.getLogisticCode());
        return orderResult;
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