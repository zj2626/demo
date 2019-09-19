package com.demo.common.service.algorithm.math.search;

import com.demo.common.service.algorithm.math.data.Arrayss;
import org.junit.Test;

import java.util.Arrays;

// 二分法 时间复杂度: log2^n
public class Dichotomy extends Arrayss {

    @Test
    public void test() {
        //二分法
        // 1排序
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 1二分
        int need = 94;
        int min = 0;
        int max = arr.length - 1;
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            if (min < max) {
                int mid = (min + max) / 2;
                if (arr[mid] == need) {
                    System.out.println("got it in " + mid);
                    result = mid;
                    break;
                } else if (arr[mid] < need) {
                    min = mid;
                } else {
                    max = mid;
                }
            } else {
                System.out.println("there is not " + i);
                break;
            }
        }
        System.out.println(arr[result]);
    }

    @Test
    public void test2() {
        int[] arr = {1, 54, 26, 2, 4, 3, 6, 84, 7, 94, 54, 5, 63, 564, 3, 523, 99};
        System.out.println(arr.length);
        System.out.println(Arrays.toString(arr));

        //二分法
        // 1排序
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 1二分
        int need = 94;
        int min = 0;
        int max = arr.length - 1;
        int result = recursion(min, max, need, arr);
        System.out.println(arr[result]);
    }

    private int recursion(int min, int max, int need, int[] arr) {
        if (min < max) {
            int mid = (min + max) / 2;
            if (arr[mid] == need) {
                System.out.println("got it in " + mid);
                return mid;
            } else if (arr[mid] < need) {
                min = mid;
                return recursion(min, max, need, arr);
            } else {
                max = mid;
                return recursion(min, max, need, arr);
            }
        } else {
            System.out.println("there is not " + need);
        }
        return -1;
    }
}
