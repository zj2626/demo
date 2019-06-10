package com.kdniao.logisticsfront.common.biz.service.impl.spring.injection;

import org.springframework.stereotype.Component;

public class UserDao {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
