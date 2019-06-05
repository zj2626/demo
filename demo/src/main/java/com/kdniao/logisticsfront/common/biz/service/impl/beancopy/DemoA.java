package com.kdniao.logisticsfront.common.biz.service.impl.beancopy;


import java.util.Date;

public class DemoA {
    private String name;
    private Integer age;
    private int age2;
    private Double money;
    private float moneyF;
    private java.util.Date uDate;
    private java.sql.Date sDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getuDate() {
        return uDate;
    }

    public void setuDate(Date uDate) {
        this.uDate = uDate;
    }

    public java.sql.Date getsDate() {
        return sDate;
    }

    public void setsDate(java.sql.Date sDate) {
        this.sDate = sDate;
    }

    public float getMoneyF() {
        return moneyF;
    }

    public void setMoneyF(float moneyF) {
        this.moneyF = moneyF;
    }
}
