package com.demo.common.service.spring.learn5.ioc.bean;

public class DemoBeanServiceImpl extends FunnyTest implements DemoBeanService {
    // 通过直接设置属性注入
    private String codeName;
    // 通过构造方法注入
    private DemoDao dao;
    // 通过直接设置属性注入
    private DemoInsertBeanDao iDao;
    // 通过直接设置属性注入(byName/byName自动注入)
    private DemoDeleteBeanDao dtDao;

    public DemoBeanServiceImpl(DemoDao demoQueryDao) {
        this.dao = demoQueryDao;
    }

    @Override
    public String getMsg(String msg) {
        System.out.println("codeName is " + codeName);
        dao.doQuery();
        iDao.doInsert();
        dtDao.doDelete();
        return "Got Message " + msg;
    }
}
