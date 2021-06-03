package com.demo.common.service.net.netty.learn3.pojo.test1;

import java.io.Serializable;

public class Entity implements Serializable {
    private static final long serialVersionUID = -6236340795725143988L;

    private String name;
    private Integer sex = 0;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"sex\":")
                .append(sex);
        sb.append('}');
        return sb.toString();
    }
}
