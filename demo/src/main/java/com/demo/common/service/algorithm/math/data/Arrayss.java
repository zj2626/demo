package com.demo.common.service.algorithm.math.data;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;

public class Arrayss {
    protected static Integer[] arr;
    protected static int loopCount = 0;
    protected static int exchangeNumber = 0;

    @Before
    public void init() {
        // 正向
        arr = new Integer[]{2, 1, 10, 5, 3, 4, 7, 9, 11, 8};
//        arr = new Integer[]{52, 39, 67, 95, 70, 8, 25, 52, 8, 7, 99, 99, 21, 39, 52};

        // 反向
        ArrayUtils.reverse(arr);

        System.out.println("长度: " + arr.length);
        System.out.println("<排序操作前> \n" + Arrays.toString(arr) + "\n<排序操作前> \n");
    }

    @After
    public void print() {
        System.out.println("\n" + "<排序操作后> \n" + Arrays.toString(arr) + "\n<排序操作后>");
        System.out.println("循环次数: " + loopCount);
        System.out.println("位移次数: " + exchangeNumber);
    }

    public void log(boolean ifMain){
            System.out.println((ifMain ? "" : " ") + Arrays.toString(arr));
    }

    protected void exchange(Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        log(false);
        exchangeNumber++;
    }
}
