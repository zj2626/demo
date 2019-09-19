package com.demo.common.service.algorithm.math.sort;

import com.demo.common.service.algorithm.math.data.Arrayss;
import org.junit.Test;

/**
 * 希尔排序
 */
public class ShellSort extends Arrayss {
    /*
     8   11  9   7   4   3   5   10  1   2

     当每组2个元素:
     8                   3
         11                  5
             9                   10
                 7                   1
                     4                   2

     当每组3个元素:
     8           7           5
         11          4           10
             9           3           1

    */

    @Test
    public void test() {
        int number = arr.length / 2;

        // 循环直到number=0,也就去全部在一个组
        while (number > 0) {
            // 循环每个组:例:第一次是每组2个元素,也就是分为5个组(number=5)

            // 直接插入排序
            // *******************************
            for (int i = number; i < arr.length; i++) {
                int temp = arr[i];
                int j = i - number;

                //直接插入排序，会向前找所适合的位置
                while (j >= 0 && arr[j] > temp) {
                    //向后移动number位
                    arr[j + number] = arr[j];
                    j = j - number;

                    loopCount++;
                }
                arr[j + number] = temp;

                log(false);
            }
            // *******************************

            number = number / 2;

            log(true);
        }
    }

//    @Test
//    public void test2() {
//        int number = arr.length / 2;
//
//        while (number > 0) {
//            for (int i = number; i < arr.length; i++) {
//                int temp = arr[i];
//                int j = i - number;
//
//                while (j >= 0 && arr[j] > temp) {
//                    arr[j + number] = arr[j];
//                    j = j - number;;
//                }
//                arr[j + number] = temp;
//
//                log(false);
//            }
//
//            number = number / 2;
//        }
//    }
}
