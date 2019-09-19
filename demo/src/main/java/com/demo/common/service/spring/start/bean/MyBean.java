package com.demo.common.service.spring.start.bean;

public class MyBean implements BeanInterface{
    private int id;
    private String name;

    public MyBean() {
        System.out.println(MyBean.class.getSimpleName()+ " | construction");
    }

    @Override
    public int getId() {
        System.out.println(this.getClass().getSimpleName() + " | getId");
        return id;
    }

    @Override
    public void setId(int id) {
        System.out.println(this.getClass().getSimpleName() + " | setId");
        this.id = id;
    }

    @Override
    public String getName() {
        System.out.println(this.getClass().getSimpleName() + " | getName");
        return name;
    }

    @Override
    public void setName(String name) {
        System.out.println(this.getClass().getSimpleName() + " | setName");
        this.name = name;
    }

    protected void show() {
        System.out.println("show what");
    }

    private void show2() {
        System.out.println("show fucking what");
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
