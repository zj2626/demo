package com.demo.common.service.design.structural.composite.design.map;

// 抽象构件 节点的抽象 可以代表叶子构件或者容器构件
public interface Component {
    public void add(Component component) throws Exception;

    public void remove(Component component) throws Exception;

    public void getChild(int i) throws Exception;

    public void display();
}
