package com.demo.common.service.design.structural.composite.design.map;

// 叶子构件 相当于叶子节点(文件) 不能拥有子节点
public class Leaf implements Component {
    private String name;

    @Override
    public void add(Component component) throws Exception {
        // 禁止
        throw new Exception("禁止");
    }

    @Override
    public void remove(Component component) throws Exception {
        // 禁止
        throw new Exception("禁止");
    }

    @Override
    public void getChild(int i) throws Exception {
        // 禁止
        throw new Exception("不存在");
    }

    @Override
    public void display() {
        System.out.println(" 文件: " + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
