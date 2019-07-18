package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

/**
 * 折半插入查找
 */
public class BinaryInsertionSort extends Arrayss {
    @Test
    public void test() {
        for (int i = 1; i < arr.length; i++) {

            int index = binary(arr, 0, i, arr[i]);

            // 获得下标 一个一个移动元素插入
            for (int j = i; j > index; j--) {
                exchange(arr, j - 1, j);
            }

            log(true);
        }
    }

    // 二分法查找比待插入值稍大的位置, 返回位置下标 (确保返回的是即将向后移动的第一个元素)
    private int binary(Integer[] arr, int min, int max, int key) {
        while (min <= max) {
            int middle = (min + max) / 2;

            if (arr[middle] < key) {
                min = middle + 1;
            } else {
                max = middle - 1;
            }

            loopCount++;
        }

        return min;
    }


}
