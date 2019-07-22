package com.kdniao.logisticsfront.common.biz.service.impl.thread.system.chain;

public class Request {
    private String name;

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
