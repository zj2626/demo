package com.demo.common.service.spring.start.bean;

public class DemoFather {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DemoFather{" +
                "id=" + id +
                '}';
    }
}