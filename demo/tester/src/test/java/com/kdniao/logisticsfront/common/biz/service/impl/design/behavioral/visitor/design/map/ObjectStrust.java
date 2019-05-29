package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.visitor.design.map;

import java.util.ArrayList;
import java.util.List;

public class ObjectStrust {
    private List<Element> elements = new ArrayList<>();

    public void operation() {

    }

    public void add(Element element){
        elements.add(element);
    }

    public void remove(Element element){
        elements.remove(element);
    }

    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }
}
