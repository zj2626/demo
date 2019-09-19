package com.demo.common.service.spring.demo;

public class InjectUserDao3 {
    private UserDao userDao;

    public InjectUserDao3(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU() {
        return userDao;
    }
}
