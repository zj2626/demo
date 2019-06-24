package com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean;

public class MyBeanDao {
    private MyBean myBean;

    public MyBeanDao() {
        System.out.println(MyBeanDao.class.getSimpleName());
    }

    public MyBean getMyBean() {
        return myBean;
    }

    public void setMyBean(MyBean myBean) {
        this.myBean = myBean;
    }

    @Override
    public String toString() {
        return "MyBeanDao{" +
                "myBean=" + myBean +
                '}';
    }
}
