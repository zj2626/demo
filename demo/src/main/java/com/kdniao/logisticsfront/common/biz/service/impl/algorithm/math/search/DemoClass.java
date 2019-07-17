package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.search;

import java.util.Objects;

public class DemoClass {
    private String name;
    private int age;
    private int sex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemoClass demoClass = (DemoClass) o;
        return age == demoClass.age &&
                sex == demoClass.sex &&
                Objects.equals(name, demoClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
    }
}
