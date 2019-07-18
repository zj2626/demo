package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;

public class Arrayss {
    protected static Integer[] arr;
    protected static int n = 0;

    @Before
    public void init() {
        // 正向
        arr = new Integer[]{2, 1, 10, 5, 3, 4, 7, 9, 11, 8};
//        arr = new Integer[]{52, 39, 67, 95, 70, 8, 25, 52, 8, 7, 99, 99, 21, 39, 52};

        // 反向
        ArrayUtils.reverse(arr);

        System.out.println("长度: " + arr.length);
        System.out.println("<操作前> \n" + Arrays.toString(arr) + "\n<操作前> \n");
    }

    @After
    public void print() {
        System.out.println("\n" + "<操作后> \n" + Arrays.toString(arr) + "\n<操作后>");
        System.out.println("次数: " + n);
    }
}
