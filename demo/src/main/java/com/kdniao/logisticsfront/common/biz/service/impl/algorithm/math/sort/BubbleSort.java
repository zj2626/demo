package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort extends Arrayss {
    // 就是每个和本行的后面的一个一个对比, 把最小的移动到最左边 或者 (同理)把最大的移动到最右边
    @Test
    public void test() {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
                n++;
            }

            System.out.println(Arrays.toString(arr));
        }
    }
}
