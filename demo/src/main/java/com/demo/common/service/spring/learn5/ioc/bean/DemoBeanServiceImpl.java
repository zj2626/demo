package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoBeanServiceImpl implements DemoBeanService {
    private DemoDao dDao;

    @Override
    public String getMsg(String msg) {
        System.out.println("QAAAAAAAAAAAA  AAAAAA " + msg);
        return "EWWWWWWWWWWWWWWW ";
    }

    public void setdDao(DemoDao dDao) {
        this.dDao = dDao;
    }
}
