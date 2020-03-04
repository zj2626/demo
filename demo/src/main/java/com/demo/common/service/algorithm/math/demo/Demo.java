package com.demo.common.service.algorithm.math.demo;

import org.junit.Test;

public class Demo {

    @Test
    public void test(){
        Integer[] sum ={-2, 1, -3, 4, -1, 2, 1, -5, 4};

        int start = 0;
        for(; start < sum.length; start++){
            for(int count = 1; count < sum.length; count++){
                System.out.println(start + " --- " + count);
            }
        }
    }
}
