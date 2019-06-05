/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.core.model.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author huangboke
 * @version $Id: OrderStatusNotifyResponse.java, v 0.1 2017年7月26日 下午3:40:59 huangboke Exp $
 */
@XmlRootElement(name = "Response")
public class OrderStatusNotifyResponse {

    public static final String RESPONSE_CODE_INVALID_XML = "S01";
    public static final String RESPONSE_CODE_INVALID_SIGN = "S02";
    public static final String RESPONSE_CODE_EMPTY_REQUEST = "S05";
    public static final String RESPONSE_CODE_SYSTEM_ERROR = "S07";

    /// [XmlElement("logisticProviderID")]
    private String logisticProviderID;

    /// [XmlElement("txLogisticID")]
    private String txLogisticID;

    /// [XmlElement("success")]
    private String success;

    /// [XmlElement("reason")]
    private String reason;

    @XmlElement(name = "logisticProviderID")
    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    @XmlElement(name = "txLogisticID")
    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    @XmlElement(name = "success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @XmlElement(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}