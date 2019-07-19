package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

/**
 * 冒泡排序
 * <p>
 * 比较相邻的元素，如果第一个比第二个大，就交换它们，从开始的第一对到结尾最后一对作相同的工作，第一轮下来会将最大的数冒到最右边，然后区间右边减少一个，重复上述步骤，直到区间缩小为1
 */
public class BubbleSort extends Arrayss {
    // 把最小的移动到最左边
    @Test
    public void test() {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    exchange(arr, i, j);
                }
                loopCount++;
            }

            System.out.println(Arrays.toString(arr));
        }
    }

    // 把最大的移动到最右边
    @Test
    public void test2() {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchange(arr, j, j + 1);
                }
                loopCount++;
            }

            System.out.println(Arrays.toString(arr));
        }
    }


    // 把最大的移动到最右边  确定前面的都已经排好序之后就不再循环外部循环 (优化)
    @Test
    public void test2better() {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = false;

            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    exchange(arr, j, j + 1);
                    flag = true;
                }
                loopCount++;
            }

            if (!flag) {
                break;
            }

            System.out.println(Arrays.toString(arr));
        }
    }
}
