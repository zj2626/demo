package com.demo.common.service.basic;

import com.demo.common.service.basic.bean.A;
import com.demo.common.service.basic.bean.BeanA;
import com.demo.common.service.basic.bean.ResultInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化顺序
 * 1. 静态变量/静态语句块 -> 实例变量/普通语句块 -> 构造方法
 * 2.
 * 父类（静态变量、静态语句块）
 * 子类（静态变量、静态语句块）
 * 父类（实例变量、普通语句块）
 * 父类（构造函数）
 * 子类（实例变量、普通语句块）
 * 子类（构造函数）
 */

public class StaticDemo {

    public static void main(String[] args) {
        A a = new A();
        System.out.println("--------------");
        A a1 = new A();
        System.out.println("--------------");
        A.C c = new A.C();
    }

    @Test
    public void test(){
        BeanA beanA = new BeanA();
        beanA.setName("AAA");
        BeanA beanB = new BeanA();
        beanA.setName("BBB");

        BeanA.BeanB b = new BeanA.BeanB();
        b.setAge("aa");
        beanA.setB(b);
        BeanA.BeanB b2 = new BeanA.BeanB();
        b2.setAge("bb");
        beanB.setB(b2);

        System.out.println(beanA.getB());
        System.out.println(beanB.getB());
        System.out.println();
        System.out.println(beanA.getB().getSingle());
        System.out.println(beanB.getB().getSingle());
        System.out.println(BeanA.BeanB.getSingle());
    }

    @Test
    public void test2(){
        ResultInfo info = new ResultInfo();
        info.setResult(1);
        List<ResultInfo.SendDetail> sendDetails = new ArrayList<>();
        info.setSendDetails(sendDetails);

        ResultInfo info2 = new ResultInfo();
        info2.setResult(1);
        List<ResultInfo.SendDetail> sendDetails2 = new ArrayList<>();
        info2.setSendDetails(sendDetails2);

        System.out.println(info.getSendDetails().hashCode());
        System.out.println(info2.getSendDetails().hashCode());
    }
}
