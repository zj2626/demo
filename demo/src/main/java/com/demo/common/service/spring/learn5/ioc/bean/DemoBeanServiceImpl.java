package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoBeanServiceImpl implements DemoBeanService {
    private String codeName;
    private DemoDao dDao;
    private DemoInsertBeanDao iDao;

    public DemoBeanServiceImpl(DemoDao dDao) {
        this.dDao = dDao;
    }

    @Override
    public String getMsg(String msg) {
        System.out.println("codeName is " + codeName);
        iDao.doInsert();
        dDao.doQuery();
        return "Got Message " + msg;
    }
}
