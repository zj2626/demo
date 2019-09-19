package com.demo.common.service.design.structural.composite.design.map;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

// 容器构件 相当于非叶子节点的子节点(文件夹) 可以拥有子节点(可以是容器构件或者叶子构件)
public class Composite implements Component {
    private String name;
    private List<Component> childComponent = new ArrayList<Component>();

    @Override
    public void add(Component component) {
        childComponent.add(component);
    }

    @Override
    public void remove(Component component) {
        childComponent.remove(component);
    }

    @Override
    public void getChild(int i) {
        childComponent.get(i);
    }

    @Override
    public void display() {
        System.out.println("文件夹: " + name);

        if (!CollectionUtils.isEmpty(childComponent)) {
            for (Component component : childComponent) {
                component.display();
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
