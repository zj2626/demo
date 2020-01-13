package com.demo.common.service.spring.learn3.proxy.dynamic1;

import com.demo.common.service.spring.learn3.proxy.dynamic1.bean.Person;

public interface TargetInterface {
    void action1();

    void action2(String msg, Integer age, Person person);
}
