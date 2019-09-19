package com.demo.common.service.toString;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToStringTest {
    public static void main(String args[]){
        Person0 person0 = new Person0();
        person0.setName("zj23232323");
        person0.setAge(20);
        person0.setNum(123.456);
        System.out.println(person0.toString() + '\n');
        System.out.println(person0.toStrings());
    }
}

class Person0{
    private String name;
    private Integer age;
    private Double num;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String toStrings(){
        System.out.println(ToStringBuilder.reflectionToString(this));
        System.out.println(ToStringBuilder.reflectionToString(this,ToStringStyle.DEFAULT_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(this,ToStringStyle.NO_FIELD_NAMES_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(this,ToStringStyle.SIMPLE_STYLE));
        return null;
    }

    @Override
    public String toString() {
        return "{" + "\"name\":\"" +
                name + '\"' +
                ",\"age\":" +
                age +
                ",\"num\":" +
                num +
                ",\"info\":" +
                info +
                '}';
    }
}
