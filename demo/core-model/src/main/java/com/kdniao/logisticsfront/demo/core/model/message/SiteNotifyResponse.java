/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.core.model.message;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author huangboke
 * @version $Id: OrderStatusNotifyResponse.java, v 0.1 2017年7月26日 下午3:40:59 huangboke Exp $
 */

public class SiteNotifyResponse {

    public static final String RESPONSE_CODE_INVALID_XML = "S01";
    public static final String RESPONSE_CODE_INVALID_SIGN = "S02";
    public static final String RESPONSE_CODE_EMPTY_REQUEST = "S05";
    public static final String RESPONSE_CODE_SYSTEM_ERROR = "S07";

    /// [XmlElement("logisticProviderID")]
    private String code;

    /// [XmlElement("txLogisticID")]
    private String msg;

    /// [XmlElement("success")]
    private String success;

    /// [XmlElement("reason")]
    private String data;
    @JSONField(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @JSONField(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @JSONField(name = "success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    @JSONField(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}