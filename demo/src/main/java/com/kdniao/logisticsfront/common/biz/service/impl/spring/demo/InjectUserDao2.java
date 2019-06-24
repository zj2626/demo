package com.kdniao.logisticsfront.common.biz.service.impl.spring.demo;

public class InjectUserDao2 {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU() {
        return userDao;
    }
}
