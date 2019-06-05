package com.kdniao.logisticsfront.demo.core.model.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class IdentityAuthenticationRequest implements Serializable {
    @JSONField(name = "Name")
    private String name; // String	R	真实姓名（个人在公安户籍管理部门正式登记注册的姓名）
    @JSONField(name = "Gender")
    private String gender; //	String	O	性别：1-男 ，2-女，3-未知
    @JSONField(name = "TypeOfId")
    private String typeOfId; //String	R	个人有效身份证件类型代码：01-居民身份证，02-临时居民身份证，03-户口薄，04-中国人民解放军军人身份证，05-中国人民武装警察身份证，06-港澳居民来往内地通行证，07-台湾居民来往大陆通行证，08-外国公民护照，09-中国公民护照
    @JSONField(name = "NumberOfId")
    private String numberOfId; //	String	R	证件号
    @JSONField(name = "Contact")
    private List<IdentityContactp> contact; //	[联系方式，数组形式，有多个电话或手机，可以多填写]
    @JSONField(name = "Addresses")
    private List<IdentityAddresses> addresses; //[地址信息，数组形式，支持多个值传输]
    @JSONField(name = "CodeOfWeixin")
    private String codeOfWeixin; //	String	O	微信号
    @JSONField(name = "TypeOfUser")
    private String typeOfUser; //	String	R	用户类别：1-非协议用户，2-协议用户
    @JSONField(name = "CollectMethod")
    private String collectMethod; //	String	R	用户信息采集方式
    /*
    10：身份证件识别设备
    20：NFC+SAM方式
    30：OCR读取方式
    40：用户自助
    50：收派员手工
    99：其它
    */
    @JSONField(name = "UserImage")
    private String userImage; //	String	O 	用户头像（图片BASE64编码）
    @JSONField(name = "Type")
    private String type; //	String	O	用户类型：10-个人散件用户，11-个人协议用户
    @JSONField(name = "StoreName")
    private String storeName; //	String	C	网店名称（对应的店铺名称）
    @JSONField(name = "BellowStationCode")
    private String bellowStationCode; //	String	O	网店所属快递营业网点编号，如为个人协议用户，填写网店所属快递营业网点编号
    @JSONField(name = "BellowStationName")
    private String bellowStationName; //	String	O	网店所属快递营业网点名称，如为个人协议用户，填写网店所属快递营业网点名称
    @JSONField(name = "BellowStationAddr")
    private String bellowStationAddr; //	String	O	网店所属快递营业网点地址，如为个人协议用户，填写网店所属快递营业网点地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTypeOfId() {
        return typeOfId;
    }

    public void setTypeOfId(String typeOfId) {
        this.typeOfId = typeOfId;
    }

    public String getNumberOfId() {
        return numberOfId;
    }

    public void setNumberOfId(String numberOfId) {
        this.numberOfId = numberOfId;
    }

    public List<IdentityContactp> getContact() {
        return contact;
    }

    public void setContact(List<IdentityContactp> contact) {
        this.contact = contact;
    }

    public List<IdentityAddresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<IdentityAddresses> addresses) {
        this.addresses = addresses;
    }

    public String getCodeOfWeixin() {
        return codeOfWeixin;
    }

    public void setCodeOfWeixin(String codeOfWeixin) {
        this.codeOfWeixin = codeOfWeixin;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getCollectMethod() {
        return collectMethod;
    }

    public void setCollectMethod(String collectMethod) {
        this.collectMethod = collectMethod;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBellowStationCode() {
        return bellowStationCode;
    }

    public void setBellowStationCode(String bellowStationCode) {
        this.bellowStationCode = bellowStationCode;
    }

    public String getBellowStationName() {
        return bellowStationName;
    }

    public void setBellowStationName(String bellowStationName) {
        this.bellowStationName = bellowStationName;
    }

    public String getBellowStationAddr() {
        return bellowStationAddr;
    }

    public void setBellowStationAddr(String bellowStationAddr) {
        this.bellowStationAddr = bellowStationAddr;
    }
}
