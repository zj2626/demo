package com.kdniao.logisticsfront.common.biz.service.impl.beancopy;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class BeanDemo {
    public static void main(String[] args) {
        DemoA demoA = new DemoA();
        demoA.setName("aaa");
        demoA.setAge(1);
        demoA.setAge2(2);
        demoA.setMoney(3.01D);
        demoA.setuDate(new Date());
        demoA.setsDate(new java.sql.Date(new java.util.Date().getTime()));
        System.out.println(JSON.toJSONString(demoA));

        DemoB demoB = new DemoB();
        org.springframework.beans.BeanUtils.copyProperties(demoA, demoB);
        System.out.println(JSON.toJSONString(demoB));

        DemoB demoC = new DemoB();
        try {
            BeanUtils.copyProperties(demoC, demoA);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(demoC));
    }
}
