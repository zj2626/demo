package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

/**
 * 直接插入排序
 */
public class StraightInsertionSort extends Arrayss {

    // 从第二个数开始, 和之前的数进行一一对比, 比较要从后往前比较,效率高
    @Test
    public void test() {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            // 这里就是遍历之前的数 从后往前比较
            for (int j = i - 1; j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
                arr[j] = temp;
                n++;
            }

            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void test2() {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            // 这里就是遍历之前的数 从前往后比较
            for (; j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
                n++;
            }
            arr[j + 1] = temp;

            System.out.println(Arrays.toString(arr));
        }
    }
}
