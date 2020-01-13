package com.demo.common.service.spring.learn3.proxy.dynamic.jdk;

import com.demo.common.service.spring.learn3.proxy.dynamic.jdk.bean.Person;

public class TargetClass implements TargetInterface {

    @Override
    public String action1() {
        return "AAAAAAAAAAA";
    }

    @Override
    public void action2(String msg, Integer age, Person person) {
        System.out.println("222" + msg + "|" + age + "| -- " + person.toString());
    }
}
