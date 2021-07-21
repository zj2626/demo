package com.demo.common.service.spring.start.bean;

public class MyBeanService {
    private MyBean myBean;
    private int id;
    private String name;

    private Long time;

    public MyBeanService() {
        System.out.println(MyBeanService.class.getSimpleName());
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
        return "MyBeanService{" +
                "myBean=" + myBean +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void exec(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis();
        System.out.printf("线程 %s, 调用时间 %s IN ~~~\n",
                Thread.currentThread().getName(),
                time);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("线程 %s, 调用时间 %s OUT ~~~\n",
                Thread.currentThread().getName(),
                time);
    }
}
