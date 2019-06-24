package com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean;

public class MyBean {
    private int id;
    private String name;

    public MyBean() {
        System.out.println(MyBean.class.getSimpleName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
