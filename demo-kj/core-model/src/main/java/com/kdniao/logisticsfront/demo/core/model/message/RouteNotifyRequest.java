/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.core.model.message;

/**
 *
 * @author huangboke
 * @version $Id: RouteNotifyRequest.java, v 0.1 2017年7月26日 下午3:40:59 huangboke Exp $
 */
//@XmlRootElement(name = "UpdateInfo")
public class RouteNotifyRequest {

    private String customerCode;
    private String orderLogisticsCode;
    private String orderChannelCode;
    private String waybillNo;
    private String code;
    private String remark;
    private String weight;
    private String freightFee;
    private String takingEmpName;
    private String takingEmpMobile;
    private String takingTime;
    private String signedName;
    private String signedTime;
    private String deliveryEmpName;
    private String deliveryEmpMobile;
    private String deliveryTime;
    private String createTime;
    private String abnormalCode;
    private String abnormalDesc;
    private String abnormalTime;
    private String assignTime;
    private String assignEmpName;
    private String assignEmpMobile;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrderLogisticsCode() {
        return orderLogisticsCode;
    }

    public void setOrderLogisticsCode(String orderLogisticsCode) {
        this.orderLogisticsCode = orderLogisticsCode;
    }

    public String getOrderChannelCode() {
        return orderChannelCode;
    }

    public void setOrderChannelCode(String orderChannelCode) {
        this.orderChannelCode = orderChannelCode;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFreightFee() {
        return freightFee;
    }

    public void setFreightFee(String freightFee) {
        this.freightFee = freightFee;
    }

    public String getTakingEmpName() {
        return takingEmpName;
    }

    public void setTakingEmpName(String takingEmpName) {
        this.takingEmpName = takingEmpName;
    }

    public String getTakingEmpMobile() {
        return takingEmpMobile;
    }

    public void setTakingEmpMobile(String takingEmpMobile) {
        this.takingEmpMobile = takingEmpMobile;
    }

    public String getTakingTime() {
        return takingTime;
    }

    public void setTakingTime(String takingTime) {
        this.takingTime = takingTime;
    }

    public String getSignedName() {
        return signedName;
    }

    public void setSignedName(String signedName) {
        this.signedName = signedName;
    }

    public String getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(String signedTime) {
        this.signedTime = signedTime;
    }

    public String getDeliveryEmpName() {
        return deliveryEmpName;
    }

    public void setDeliveryEmpName(String deliveryEmpName) {
        this.deliveryEmpName = deliveryEmpName;
    }

    public String getDeliveryEmpMobile() {
        return deliveryEmpMobile;
    }

    public void setDeliveryEmpMobile(String deliveryEmpMobile) {
        this.deliveryEmpMobile = deliveryEmpMobile;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAbnormalCode() {
        return abnormalCode;
    }

    public void setAbnormalCode(String abnormalCode) {
        this.abnormalCode = abnormalCode;
    }

    public String getAbnormalDesc() {
        return abnormalDesc;
    }

    public void setAbnormalDesc(String abnormalDesc) {
        this.abnormalDesc = abnormalDesc;
    }

    public String getAbnormalTime() {
        return abnormalTime;
    }

    public void setAbnormalTime(String abnormalTime) {
        this.abnormalTime = abnormalTime;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getAssignEmpName() {
        return assignEmpName;
    }

    public void setAssignEmpName(String assignEmpName) {
        this.assignEmpName = assignEmpName;
    }

    public String getAssignEmpMobile() {
        return assignEmpMobile;
    }

    public void setAssignEmpMobile(String assignEmpMobile) {
        this.assignEmpMobile = assignEmpMobile;
    }
}