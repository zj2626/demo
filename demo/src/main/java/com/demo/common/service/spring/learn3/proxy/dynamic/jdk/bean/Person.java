package com.demo.common.service.spring.learn3.proxy.dynamic.jdk.bean;

public class Person {
    private String kind;

    public Person(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"kind\":\"")
                .append(kind).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
