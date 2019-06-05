package com.kdniao.logisticsfront.common.biz.service.impl.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;
    private String address;
    private Float foods;
    private Double sexs;

    public User() {
        super();
    }

    public User(String name, Integer age, String address, Float foods, Double sexs) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.foods = foods;
        this.sexs = sexs;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getFoods() {
        return foods;
    }

    public void setFoods(Float foods) {
        this.foods = foods;
    }

    public Double getSexs() {
        return sexs;
    }

    public void setSexs(Double sexs) {
        this.sexs = sexs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"age\":")
                .append(age);
        sb.append(",\"address\":\"")
                .append(address).append('\"');
        sb.append(",\"foods\":")
                .append(foods);
        sb.append(",\"sexs\":")
                .append(sexs);
        sb.append('}');
        return sb.toString();
    }
}
