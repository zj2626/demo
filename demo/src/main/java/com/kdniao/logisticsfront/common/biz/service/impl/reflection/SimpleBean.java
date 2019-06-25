package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

public class SimpleBean implements Bean {
    private String name;

    public SimpleBean() {
        System.out.println("SimpleBean" + "\n");
    }

    @Override
    public String getName() {
        return name;
    }

    public SimpleBean setName(String name) {
        this.name = name;
        return this;
    }

    protected void show() {
        System.out.println("AAAAAAA");
    }

    private void show2() {
        System.out.println("BBBBB");
    }
}
