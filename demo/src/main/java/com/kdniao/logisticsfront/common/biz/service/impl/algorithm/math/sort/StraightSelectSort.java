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
            // 初始化下标, 随着内部for执行, 下标变成最小值所在下标
            int current = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[current] > arr[j]) {
                    current = j;
                }
                loopCount++;
            }

            int temp = arr[i];
            // i位置和最小值位置数据进行交换,如果i已经是最小值就不执行交换
            if (temp != arr[current]) {
                arr[i] = arr[current];
                arr[current] = temp;
            }

            System.out.println(Arrays.toString(arr));
        }
    }
}
