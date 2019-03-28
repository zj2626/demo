package com.kdniao.logisticsfront.demo.core.model.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class IdentityAddresses implements Serializable {
    @JSONField(name = "AddressType")
    private String addressType; //String	R	地址类型：1-证件地址，2-常用地址

    @JSONField(name = "ProvinceName")
    private String provinceName; //	String	R	地址中的省、自治区或直辖市
    @JSONField(name = "CityName")
    private String cityName; //	String	R	地址中的市
    @JSONField(name = "ExpAreaName")
    private String expAreaName; //	String	R	地址中的县或区

    @JSONField(name = "ProvinceCode")
    private String provinceCode; //	String	R	地址中的省、自治区或直辖市 code
    @JSONField(name = "CityCode")
    private String cityCode; //	String	R	地址中的市 code
    @JSONField(name = "ExpAreaCode")
    private String expAreaCode; //	String	R	地址中的县或区 code

    @JSONField(name = "Address")
    private String address; //	String	R	详细地址

    public IdentityAddresses() {
    }

    public IdentityAddresses(String addressType, String provinceName, String cityName, String expAreaName, String address) {
        this.addressType = addressType;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.expAreaName = expAreaName;
        this.address = address;
    }

    public IdentityAddresses(String addressType, String address, String provinceName, String cityName, String expAreaName,
                             String provinceCode, String cityCode, String expAreaCode) {
        this.addressType = addressType;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.expAreaName = expAreaName;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.expAreaCode = expAreaCode;
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getExpAreaName() {
        return expAreaName;
    }

    public void setExpAreaName(String expAreaName) {
        this.expAreaName = expAreaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getExpAreaCode() {
        return expAreaCode;
    }

    public void setExpAreaCode(String expAreaCode) {
        this.expAreaCode = expAreaCode;
    }
}
