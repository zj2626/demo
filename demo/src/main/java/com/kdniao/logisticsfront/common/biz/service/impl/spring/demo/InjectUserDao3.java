package com.kdniao.logisticsfront.common.biz.service.impl.spring.demo;

public class InjectUserDao3 {
    private UserDao userDao;

    public InjectUserDao3(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU() {
        return userDao;
    }
}