package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.prototype;

public class Prop implements Cloneable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*深复制*/
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
