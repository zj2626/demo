package com.demo.common.service.design.behavioral.iterator.design.map;

// 容器角色 一般是一个接口，提供一个iterator()方法，例如java中的Collection接口，List接口，Set接口等
public interface AggregateList {
    public Iterator iterator();

    public int size();

    public Object get(int index);

    public void add(Object obj);
}
