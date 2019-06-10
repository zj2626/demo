package com.kdniao.logisticsfront.common.biz.service.impl.spring.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class InjectUserDao2 {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU(){
        return userDao;
    }
}
