package com.kdniao.logisticsfront.demo.biz.delivery.impl;

import com.alibaba.fastjson.JSON;
import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.delivery.impl.AbstractCancelOrderManagerImpl;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeIOHttpSender;
import com.kdniao.logisticsfront.core.model.constants.ApplicationErrors;
import com.kdniao.logisticsgw.common.service.model.order.CancelExpOrder;
import com.kdniao.logisticsgw.common.service.order.CancelOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CancelOrderInvokeManagerImpl extends AbstractCancelOrderManagerImpl {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrderInvokeManagerImpl.class);
    private ExterfaceInvokeIOHttpSender cancelOrderIOHttpSender;

    public void setCancelOrderIOHttpSender(ExterfaceInvokeIOHttpSender cancelOrderIOHttpSender) {
        this.cancelOrderIOHttpSender = cancelOrderIOHttpSender;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CancelExpOrder doInvoke(CancelOrder order) {
        Print.out("取消订单demo ", order.getOrderCode());

        CancelExpOrder expOrder = new CancelExpOrder();
        expOrder.setSuccess(false);
        expOrder.setResultCode(ApplicationErrors.unknownError.getErrorCode());
        expOrder.setReason(ApplicationErrors.unknownError.getErrorDesc());
        expOrder.setOrderCode(order.getKdnOrderCode());

        System.out.println(JSON.toJSONString(order));

        expOrder.setSuccess(true);
        expOrder.setResultCode("100");
        expOrder.setReason("成功 gg");

        return expOrder;
    }

}