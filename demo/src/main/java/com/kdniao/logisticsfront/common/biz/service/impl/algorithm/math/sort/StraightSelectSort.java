package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

/**
 * 直接选择排序(简单选择排序)
 *
 * 假设排序表为 L[1....n] ，第i趟排序即从L[i,,,,n]  中选择关键字最小的元素与 L(i) 交换，每一趟排序可以确定一个元素的最终位置，这样经过 n-1 趟排序就可以使整个排序表有序。
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

            // i位置和最小值位置数据进行交换,如果i已经是最小值就不执行交换
            if (i != current) {
                exchange(arr, i, current);
            }
        }
    }
}
