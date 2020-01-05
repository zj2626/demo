package com.demo.common.service.spring.learn0.injection.bean;

public class DemoJavaBasedService {

    private DemoAnnotationDao dao;

    private Integer base;

    public void getDaoInfo(){
        System.out.println("注入:           " + base);
        System.out.println("注入:           " + dao);
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public DemoAnnotationDao getDao() {
        return dao;
    }

    public void setDao(DemoAnnotationDao dao) {
        this.dao = dao;
    }
}
