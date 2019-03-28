package com.kdniao.logisticsfront.common.biz.service.impl.array;

import java.util.Arrays;

public class ArraysCP {
    public static void main(String[] args) {
        String[] from = {"1", "2", "3", "4", "5", "6"};

        String[] to = Arrays.copyOfRange(from, 0, 1);
        System.out.println(Arrays.toString(to));
    }
}
