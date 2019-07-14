package com.kdniao.logisticsfront.common.biz.service.impl.math.algorithm;

import java.util.Arrays;

// 二分法
public class Dichotomy {

    public static void main(String[] args) {
        int[] arr = {1, 54, 26, 2, 4, 3, 6, 84, 7, 94, 54, 5, 63, 564, 3, 523, 99};
        System.out.println(arr.length);
        System.out.println(Arrays.toString(arr));

        //二分法
        // 1排序
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 1二分
        int need = 54;
        int min = 0;
        int max = arr.length - 1;
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            if (min < max) {
                int mid = (min + max) / 2;
                if (arr[mid] == need) {
                    System.out.println("got it in " + mid + " - " + i);
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
}
