package com.demo.common.service.design.creational.prototype;

import java.util.List;

public class Prototype implements Cloneable {
    private String name;
    private List<String> list;
    private List<String> set;
    private String home;
    private Prop prop;
    private int age;

    /*浅复制*/
//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }

    /*深复制*/
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        Prototype prototype = (Prototype) obj;
        prototype.setProp((Prop) prototype.getProp().clone());
        // list怎么解决 ？？？？

        return obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getSet() {
        return set;
    }

    public void setSet(List<String> set) {
        this.set = set;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Prop getProp() {
        return prop;
    }

    public void setProp(Prop prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"list\":")
                .append(list);
        sb.append(",\"set\":")
                .append(set);
        sb.append(",\"home\":\"")
                .append(home).append('\"');
        sb.append(",\"prop\":")
                .append(prop);
        sb.append(",\"age\":")
                .append(age);
        sb.append('}');
        return sb.toString();
    }
}
