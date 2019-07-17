package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

/**
 * 直接选择排序
 */
public class StraightSelectSort extends Arrayss {

    @Test
    public void test() {
        for (int i = 0; i < arr.length - 1; i++) {
            int current = arr[i];
            int currentIndex = i + 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (current > arr[j]) {
                    current = arr[j];
                    currentIndex = j;
                }
                n++;
            }

            int temp = arr[i];
            if (temp != current) {
                arr[i] = current;
                arr[currentIndex] = temp;
            }

            System.out.println(Arrays.toString(arr));
        }
    }
}
