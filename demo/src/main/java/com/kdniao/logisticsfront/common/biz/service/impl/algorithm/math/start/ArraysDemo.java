package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.start;

import java.util.Arrays;

/*数组*/
public class ArraysDemo {

    public static void main(String[] args) {
        MyArrays myArrays = new MyArrays();
        myArrays.insertOrder(23);
        myArrays.insertOrder(1);
        myArrays.insertOrder(4);
        myArrays.insertOrder(12);
        myArrays.insertOrder(2);
        myArrays.insertOrder(152);
        myArrays.insertOrder(71);
        myArrays.insertOrder(7);
        myArrays.insertOrder(17);

        System.out.println(myArrays.get(7));
        System.out.println(myArrays.get(71L));
        System.out.println(myArrays);
        myArrays.delete(3);
        System.out.println(myArrays);
        myArrays.update(3, 999);
        System.out.println(myArrays);
    }

    public static class MyArrays {
        private long[] arr;
        private int length;

        public MyArrays() {
            this.arr = new long[12];
            length = 0;
        }

        public long get(int size) {
            if (size < length) {
                return arr[size];
            }
            return -1;
        }

        public int get(long item) {
            int begin = 0;
            int end = length; //length 或者 length - 1 都一样
            while (begin < end) {
                int mid = (begin + end) / 2;
                if (arr[mid] == item) {
                    return mid;
                } else if (arr[mid] < item) {
                    begin = mid;
                } else {
                    end = mid;
                }
            }
            return -1;
        }

        /*普通数组*/
        public void insert(long item) {
            arr[length] = item;
            length++;
        }

        /* 有序数组 */
        public void insertOrder(long item) {
            int i = 0;
            for (; i < length; i++) {
                if (arr[i] > item) {
                    break;
                }
            }
            for (int j = length; j > i; j--) {
                arr[j] = arr[j - 1];
            }
            arr[i] = item;
            length++;
        }

        public void update(int size, long value) {
            if (size < length) {
                arr[size] = value;
            }
        }

        public void delete(int size) {
            if (size < length) {
                for (int i = size; i < length; i++) {
                    arr[i] = arr[i + 1];
                }
                length--;
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"arr\":")
                    .append(Arrays.toString(arr));
            sb.append(",\"length\":")
                    .append(length);
            sb.append('}');
            return sb.toString();
        }
    }
}
