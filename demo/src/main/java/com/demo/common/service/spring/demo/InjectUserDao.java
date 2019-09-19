package com.demo.common.service.spring.demo;

public class InjectUserDao implements UserRegister {
    private UserDao userDao;

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU() {
        return userDao;
    }
}
