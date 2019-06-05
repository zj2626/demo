package com.kdniao.logisticsfront.demo.core.model.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class IdentityAuthenticationResponse implements Serializable {
    @JSONField(name = "PartnerId")
    private String partnerId;//	String	R	合作商ID，由KLOP提供
    @JSONField(name = "Success")
    private String success;//	String	R	成功或失败：true,false
    @JSONField(name = "ResultCode")
    private String resultCode;//	String	R	返回编码，参照返回编码列表
    @JSONField(name = "Reason")
    private String reason;//	String	O	错误原因/备注
    @JSONField(name = "UserCode")
    private String userCode;//	String	R	上传成功时，返回用户序列号

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
