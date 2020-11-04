package com.demo.common.service.basic;

/**
 * @author zj2626
 * @name ObjectTest
 * @description
 * @create 2020-11-04 11:41
 **/
public class ObjectTest {
    public static void main(String[] args) {
        Object n = new Object(){
            @Override
            public boolean equals(Object obj) {
                return true;
            }
        };

        System.out.println(n.equals("AAA"));
    }
}
