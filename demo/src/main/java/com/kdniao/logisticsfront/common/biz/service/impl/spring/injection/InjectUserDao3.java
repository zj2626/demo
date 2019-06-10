package com.kdniao.logisticsfront.common.biz.service.impl.spring.injection;

import org.springframework.stereotype.Component;

@Component
public class InjectUserDao3 {
    private UserDao userDao;

    public InjectUserDao3(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getU(){
        return userDao;
    }
}
