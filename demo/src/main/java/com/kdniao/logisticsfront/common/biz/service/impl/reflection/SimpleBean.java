package com.kdniao.logisticsfront.common.biz.service.impl.reflection;

public class SimpleBean implements Bean {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public SimpleBean setName(String name) {
        this.name = name;
        return this;
    }
}
