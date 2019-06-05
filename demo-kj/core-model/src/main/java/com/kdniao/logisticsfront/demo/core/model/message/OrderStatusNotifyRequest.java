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
 * @version $Id: OrderStatusNotifyRequest.java, v 0.1 2017年7月26日 下午3:40:59 huangboke Exp $
 */
@XmlRootElement(name = "UpdateInfo")
public class OrderStatusNotifyRequest {

    //// [XmlElement("logisticProviderID"), JsonProperty("logisticProviderID")]
    private String logisticProviderID;

    /// [XmlElement("clientID"), JsonProperty("clientID")]
    private String clientID;

    /// [XmlElement("mailNo"), JsonProperty("mailNo")]
    private String mailNo;

    /// [XmlElement("txLogisticID"), JsonProperty("txLogisticID")]
    private String txLogisticID;

    /// [XmlElement("infoType"), JsonProperty("infoType")]
    private String infoType;

    /// [XmlElement("infoContent"), JsonProperty("infoContent")]
    private String infoContent;

    /// [XmlElement("remark"), JsonProperty("remark")]
    private String remark;

    /// [XmlElement("weight"), JsonProperty("weight")]
    private double weight;

    /// <summary>
    /// 签收人
    /// </summary>
    /// [XmlElement("signedName"), JsonProperty("signedName")]
    private String signedName;

    /// [XmlElement("deliveryName"), JsonProperty("deliveryName")]
    private String deliveryName;

    /// [XmlElement("acceptTime"), JsonProperty("acceptTime")]
    private String acceptTime;

    /// [XmlElement("contactInfo"), JsonProperty("contactInfo")]
    private String contactInfo;
    private String orgName;
    private String orgCode;
    private String orgPhone;
    private String empName;
    private String empCode;
    private String city;
    private String district;
    private String questionCause;
    private String extendFields;
    
    @XmlElement(name = "logisticProviderID")
    public String getLogisticProviderID() {
        return logisticProviderID;
    }

    public void setLogisticProviderID(String logisticProviderID) {
        this.logisticProviderID = logisticProviderID;
    }

    @XmlElement(name = "clientID")
    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    @XmlElement(name = "mailNo")
    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    @XmlElement(name = "txLogisticID")
    public String getTxLogisticID() {
        return txLogisticID;
    }

    public void setTxLogisticID(String txLogisticID) {
        this.txLogisticID = txLogisticID;
    }

    @XmlElement(name = "infoType")
    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    @XmlElement(name = "infoContent")
    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    @XmlElement(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement(name = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @XmlElement(name = "signedName")
    public String getSignedName() {
        return signedName;
    }

    public void setSignedName(String signedName) {
        this.signedName = signedName;
    }

    @XmlElement(name = "deliveryName")
    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    @XmlElement(name = "acceptTime")
    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    @XmlElement(name = "contactInfo")
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    @XmlElement(name = "orgName")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    @XmlElement(name = "orgCode")
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    @XmlElement(name = "orgPhone")
    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }
    @XmlElement(name = "empName")
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
    @XmlElement(name = "empCode")
    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    @XmlElement(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @XmlElement(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    @XmlElement(name = "questionCause")
    public String getQuestionCause() {
        return questionCause;
    }

    public void setQuestionCause(String questionCause) {
        this.questionCause = questionCause;
    }
    @XmlElement(name = "extendFields")
    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }
}