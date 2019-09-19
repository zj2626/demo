package com.demo.common.service.algorithm.math.sort;

import com.demo.common.service.algorithm.math.data.Arrayss;
import org.junit.Test;

/**
 * 直接插入排序
 * <p>
 * 当插入第i（i>1）个元素时，前面的data[0],data[1]……data[i-1]已经排好序。这时用data[i]的排序码与data[i-1],data[i-2],……的排序码顺序进行比较，找到插入位置即将data[i]插入，原来位置上的元素向后顺序移动。
 */
public class StraightInsertionSort extends Arrayss {

    @Test
    public void test() {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            // 这里就是遍历之前的数 从后往前比较
            int j = i - 1;
            for (; j >= 0 && arr[j] > temp; j--) {
                // 每个比当前值大的都向后移动一位
                arr[j + 1] = arr[j];
                loopCount++;
            }
            arr[j + 1] = temp;
        }
    }
}
