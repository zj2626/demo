package com.kdniao.logisticsfront.demo.core.model.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class IdentityContactp implements Serializable {
    @JSONField(name = "Mobile")
    private String mobile; //	String	R	个人有效联系电话，与固定电话至少填写一个
    @JSONField(name = "Phone")
    private String phone;  //	String	R	个人固定联系电话，包括区号、电话号码以及分机号，中间用“-”隔开，与手机至少填写一个

    public IdentityContactp() {
    }

    public IdentityContactp(String mobile, String phone) {
        this.mobile = mobile;
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
