package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

/**
 * 快速排序
 * <p>
 * 冒泡+递归+分治
 * <p>
 * 适合数据不大的情况
 */
public class Quicksort extends Arrayss {
    @Test
    public void test() {
        quick(arr, 0, arr.length - 1);
//        quickSort(arr, 0, arr.length - 1);
    }

    public void quick(Integer[] arr, int start, int end) {
        int key = arr[start];
        System.out.println(key);
        int min = start;
        int max = end;

        while (min < max) {
            while (min < max && key < arr[max]) {
                max--;
            }
            while (min < max && key > arr[min]) {
                min++;
            }

            if (min < max) {
                int temp = arr[min];
                arr[min] = arr[max];
                arr[max] = temp;
            }

        }

        System.out.println(Arrays.toString(arr));
        System.out.println(min + " - " + max);

        quick(arr, start, min - 1);
        quick(arr, min + 1, end);

//        if (min >= max) {
//            int temp = min;
//            arr[temp] = key;
//
//            if (temp > 0 && temp < arr.length - 1) {
//
//                key = arr[temp - 1];
//                quick(arr, start, temp - 1, key);
//
//                key = arr[temp + 1];
//                quick(arr, temp + 1, end, key);
//            }
//
//        } else {
//            quick(arr, min, max, key);
//        }

        System.out.println(min + "-" + max);
//        if (min >= max) {
//            int temp = min;
//            arr[temp] = key;
//        } else {
//            quick(arr, min, max);
//        }

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(Integer[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];
        System.out.println(temp);

        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;

        System.out.println(Arrays.toString(arr));

        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }

}
