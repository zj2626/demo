package com.demo.common.service.spring.start.bean;

public class DemoSon {
    private DemoFather father;

    public DemoFather getFather() {
        return father;
    }

    public void setFather(DemoFather father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "DemoSon{" +
                "father=" + father +
                '}';
    }
}