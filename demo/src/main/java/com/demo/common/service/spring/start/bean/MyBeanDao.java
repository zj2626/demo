package com.demo.common.service.spring.start.bean;

public class MyBeanDao {
    private MyBean myBean;
    private int id;
    private String name;

    public MyBeanDao() {
        System.out.println(MyBeanDao.class.getSimpleName());
    }

    public MyBean getMyBean() {
        return myBean;
    }

    public void setMyBean(MyBean myBean) {
        this.myBean = myBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyBeanDao{" +
                "myBean=" + myBean +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
