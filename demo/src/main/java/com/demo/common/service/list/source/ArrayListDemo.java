package com.demo.common.service.list.source;

/*
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListDemo<E> {

    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;

    // 默认构造方法创建的空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    // 空数组
    private static final Object[] EMPTY_ELEMENTDATA = {};

    // 真正存储数据的数组
    transient Object[] elementData;

    // 当前list真正有数据个数
    private int size;

    /**
     * @see AbstractList#modCount
     * 数据变更次数
     */
    protected transient int modCount = 0;


    /**
     * @see ArrayList#ArrayList()
     */
    public ArrayListDemo() {
        // 默认方法不创建长度为10的数组, 只是创建一个空数组, 在第一次add的时候才创建
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * @see ArrayList#ArrayList(int)
     */
    public ArrayListDemo(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * @see ArrayList#add(java.lang.Object)
     */
    public boolean add(E e) {
        // 计算数组容量是否足够, 是否需要扩容, 需要就进行扩容
        ensureCapacityInternal(size + 1);

        // add的数据插入对应位置
        elementData[size++] = e;
        return true;
    }

    // 计算数组容量是否足够, 需要扩容就进行扩容, minCapacity表示本次add之后的容量大小
    private void ensureCapacityInternal(int minCapacity) {
        // 进行扩容判断
        ensureExplicitCapacity(
                // 计算扩容后大小
                calculateCapacity(elementData, minCapacity)
        );
    }

    // 计算扩容后大小
    // 1. 第一次add(数据为空的时候), 最小容量得是默认容量(10), 低于默认容量的时候也是返回默认容量
    // 2. 不是第一次, 直接返回输入容量(也就是当前已有数据个数+1)
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        // 第一次add时候进入 得到默认容量大小
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // 如果需要的容量大于当前数组长度, 则会进行扩容(比如 11 > 10)
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    // 扩容方法
    private void grow(int minCapacity) {
        // 扩容前大小
        int oldCapacity = elementData.length;

        // 扩容后大小 每次加一半大小
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        // 如果扩容后大小还是不够用, 就直接扩容成需要的大小
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;

        // 如果扩容后大小 大于MAX_ARRAY_SIZE, 则直接扩容成 Integer.MAX_VALUE
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);

        // 进行扩容, 旧数据父子到新的list中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"elementData\":")
                .append(Arrays.toString(elementData));
        sb.append('}');
        return sb.toString();
    }
}
