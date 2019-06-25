package com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean;

public class MyBean implements BeanInterface{
    private int id;
    private String name;

    public MyBean() {
        System.out.println(MyBean.class.getSimpleName());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    protected void show() {
        System.out.println("AAAAAAA");
    }

    private void show2() {
        System.out.println("BBBBB");
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
