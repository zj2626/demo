package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.flyweight.design.map;

/**
 * 具体享元类 内部状态
 */
public class ConcreteFlyweight implements Flyweight {
    private String name;
    private String type;

    public ConcreteFlyweight(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void operation(UnsharedConcreteFlyweight unsharedConcreteFlyweight) {
        System.out.println("ConcreteFlyweight operation");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
