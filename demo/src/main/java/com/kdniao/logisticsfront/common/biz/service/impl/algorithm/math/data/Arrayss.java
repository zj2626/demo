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
        arr = new Integer[]{2, 1, 5, 3, 4, 7, 9, 10, 11, 8};

        // 反向
        ArrayUtils.reverse(arr);

        System.out.println("长度: " + arr.length);
        System.out.println("操作前: " + Arrays.toString(arr) + "\n");
    }

    @After
    public void print() {
        System.out.println("\n" + "操作后: " + Arrays.toString(arr));
        System.out.println("次数: " + n);
    }
}
