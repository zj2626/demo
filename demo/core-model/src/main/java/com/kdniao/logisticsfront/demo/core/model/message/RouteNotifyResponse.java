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
 * @version $Id: RouteNotifyResponse.java, v 0.1 2017年7月26日 下午3:40:59 huangboke Exp $
 */
@XmlRootElement(name = "Response")
public class RouteNotifyResponse {

    public static final String RESPONSE_CODE_INVALID_JSON = "S01";
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

    private String time;

    private String message;

    private Integer status;

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

    @XmlElement(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}