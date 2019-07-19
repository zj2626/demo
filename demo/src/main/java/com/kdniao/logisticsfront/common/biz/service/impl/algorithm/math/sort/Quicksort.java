package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.sort;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.data.Arrayss;
import org.junit.Test;

/**
 * 快速排序
 * <p>
 * 冒泡+递归+分治
 * <p>
 * 我写了两种实现方法 原理上是一个意思,只是移动的顺序不太一样
 * 1.test方法中是先交换其他值达到左小右大,此时停留位置就是居中位置, 然后在把停留位置值的和基准值交换 (better)
 * 2.test2方法是直接把基准值和其他值轮流交换(把大于的值交换到右边,把小于的值交换到左边), 基准值自然停留在居中位置
 * <p>
 * 适合数据不大的情况
 */
public class Quicksort extends Arrayss {
    /*自己的 quick执行的交换是先不交换基准值, 直到其他的值都已经交换,
    此时最终下标停留位置其左边都是比基准值小, 右边都是比基准值大;
    此时把停留位置值和基准值进行交换(停留位置值肯定小于基准值,因为移动是先移动的max下标, 所以永远指向小于基准值的值)*/
    @Test
    public void test() {
        quick(arr, 0, arr.length - 1);
    }

    /*自己的 quick执行的交换是直接交换基准值, 每次通过基准值位置实现对其他值的位置变化,
    直到基准值左边都是比基准值小的, 右边都是比基准值大的*/
    @Test
    public void test2() {
        quick2(arr, 0, arr.length - 1);
    }

    /*网上的1*/
    @Test
    public void test3() {
        quickSort(arr, 0, arr.length - 1);
    }

    /*网上的2*/
    @Test
    public void test4() {
        sort(arr, 0, arr.length - 1);
    }

    public void quick(Integer[] arr, int low, int high) {
        /**
         * before [8, 11, 9, 7, 4, 3, 5, 10, 1, 2]
         *
         * [8, 2, 9, 7, 4, 3, 5, 10, 1, 11]  [8, 2, 1, 7, 4, 3, 5, 10, 9, 11]  [8, 2, 1, 7, 4, 3, 5, 10, 9, 11]
         * [5, 2, 1, 7, 4, 3, 8, 10, 9, 11]
         *
         * [5, 2, 1, 3, 4, 7, 8, 10, 9, 11]  [5, 2, 1, 3, 4, 7, 8, 10, 9, 11]
         * [4, 2, 1, 3, 5, 7, 8, 10, 9, 11]
         *
         * [4, 2, 1, 3, 5, 7, 8, 10, 9, 11]
         * [3, 2, 1, 4, 5, 7, 8, 10, 9, 11]
         *
         * [3, 2, 1, 4, 5, 7, 8, 10, 9, 11]
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         *
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         *
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         * [1, 2, 3, 4, 5, 7, 8, 9, 10, 11]
         */

        // 1.先判断要排序的数组分区是数组的有效部分
        if (low >= high) {
            return;
        }

        // 2.得到基准值 默认取分区的第一个值
        int key = arr[low];
        int min = low;
        int max = high;

        while (min < max) {
            // 从尾部遍历 得到比基准值小的值的位置 直到min和max相等
            while (key <= arr[max] && min < max) {
                max--;
            }
            // 从头部遍历 得到比基准值大的值的位置 直到min和max相等
            while (key >= arr[min] && min < max) {
                min++;
            }

            // 如果不是同一个位置(min==max),则进行交换
            if (min < max) {
                exchange(arr, min, max);
            }

            loopCount++;
        }

        // 交换停留位置值和基准值, 此时min左边都是比基准值小, 右边都是比基准值大
        exchange(arr, low, min);

        log(true);

        //left
        quick(arr, low, min - 1);

        //right
        quick(arr, min + 1, high);
    }

    public void quick2(Integer[] arr, int low, int high) {
        /**
         * before [8, 11, 9, 7, 4, 3, 5, 10, 1, 2]
         *
         * base on: 8
         * [2, 11, 9, 7, 4, 3, 5, 10, 1, 8]  [2, 8, 9, 7, 4, 3, 5, 10, 1, 11]
         * [2, 1, 9, 7, 4, 3, 5, 10, 8, 11]  [2, 1, 8, 7, 4, 3, 5, 10, 9, 11]
         * [2, 1, 5, 7, 4, 3, 8, 10, 9, 11]
         *
         * left: base on: 2
         * [1, 2, 5, 7, 4, 3, 8, 10, 9, 11]
         *
         * left-right: base on: 5
         * [1, 2, 3, 7, 4, 5, 8, 10, 9, 11]  [1, 2, 3, 5, 4, 7, 8, 10, 9, 11]
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         *
         * left-right-left: base on: 3
         * [1, 2, 3, 4, 5, 7, 8, 10, 9, 11]
         *
         * right: base on: 9
         * [1, 2, 3, 4, 5, 7, 8, 9, 10, 11]
         */

        // 1.先判断要排序的数组分区是数组的有效部分
        if (low >= high) {
            return;
        }

        // 2.得到基准值 默认取分区的第一个值
        int key = arr[low];
        int min = low;
        int max = high;

        while (min < max) {
            // 从尾部遍历 得到比基准值小的值的位置
            while (key <= arr[max] && min < max) {
                max--;
            }
            //得到比基准值小的值 将其与基准值交换
            if (min < max) {
                // 位置: max -> min
                exchange(arr, min, max);
            }

            // 从头部遍历 得到比基准值大的值的位置
            while (key >= arr[min] && min < max) {
                min++;
            }
            //得到比基准值大的值 将其与基准值交换
            if (min < max) {
                // 位置: min -> max
                exchange(arr, min, max);
            }

            loopCount++;
        }

        log(true);

        //left
        quick2(arr, low, min - 1);

        //right
        quick2(arr, min + 1, high);
    }

    /****************************/

    private void quickSort(Integer[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

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

            loopCount++;

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;

        log(true);

        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }

    private void sort(Integer[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int i = low, j = high; // 设置这两个变量的目的是为了保持low和high不变
        int pivotNum = a[i]; // 基准数
        while (i < j) {
            while (a[j] >= pivotNum && j > i) { // 循环结束的条件有二：一是找到比支点小的数，二是j==i
                j--;
            }
            if (j > i) { // 由于上面循环结束的功能性有两个，对于找到比支点小的数，即j!=i，要进行位置的交换，下同
                a[i] = a[j];
                i++;
            }

            while (a[i] < pivotNum && i < j) {
                i++;
            }
            if (i < j) {
                a[j] = a[i];
                j--;
            }

            loopCount++;
        }
        a[i] = pivotNum;

        log(true);

        sort(a, low, i - 1);
        sort(a, i + 1, high);
    }

}
