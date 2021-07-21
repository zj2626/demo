package com.demo.common.service.copy;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class BeanDemo {
    public static void main(String[] args) {
        DemoA demoA = new DemoA();
        demoA.setName("aaa");
        demoA.setAge(1);
        demoA.setAge2(2);
        demoA.setMoney(3.01D);
        demoA.setMoneyF(4.02F);
        demoA.setuDate(new Date());
        demoA.setsDate(new java.sql.Date(new java.util.Date().getTime()));
        System.out.println(JSON.toJSONString(demoA));

        DemoB demoB = new DemoB();
        org.springframework.beans.BeanUtils.copyProperties(demoA, demoB);
        System.out.println(JSON.toJSONString(demoB));

        DemoB demoB1 = new DemoB();
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(demoB1, demoA);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(demoB1));

        DemoB demoB2 = new DemoB();
        try {
            org.apache.commons.beanutils.PropertyUtils.copyProperties(demoB2, demoA);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(demoB2));

        DemoC demoC = new DemoC();
        org.springframework.beans.BeanUtils.copyProperties(demoA, demoC);
        System.out.println(JSON.toJSONString(demoC));
    }
}
