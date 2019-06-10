package com.kdniao.logisticsfront.common.biz.service.impl.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InjectUserDao implements UserRegister{
    private UserDao userDao;

    @Autowired
    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU(){
        return userDao;
    }
}
