package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.interfaces.ListInterface;

import java.util.Arrays;

/**
 * 顺序表，可扩容
 */
public class MyArrayList<E> implements ListInterface<E> {
    private Object[] elementData;   //底层是数组
    private int size;               //元素的个数

    public MyArrayList() {
        elementData = new Object[]{};
    }

    public MyArrayList(int initSize) {
        elementData = new Object[initSize];
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean add(Object o) {
        // 判断是否需要扩容
        ensureCapacityInternal();

        elementData[size] = o;
        size++;
        return true;
    }

    @Override
    public void add(int index, Object element) {
        // 判断是否需要扩容
        ensureCapacityInternal();

        // 判断插入位置合法
        if (index >= this.size) {
            index = this.size;
        }

        for (int i = this.size; i > index; i++) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = element;
        size++;
    }

    private void ensureCapacityInternal() {
        if (this.size >= elementData.length - 1) {
            Object[] newElementData = new Object[size + 5];
            for (int i = 0; i < size; i++) {
                newElementData[i] = elementData[i];
            }
            elementData = newElementData;
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E remove(int index) {
        E removed = null;
        if (index >= 0 && index < this.size) {
            removed = (E) elementData[index];
            for (int i = index; i < this.size; i++) {
                elementData[i] = elementData[i + 1];
            }
        }
        return removed;
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < this.size) {
            return (E) elementData[index];
        } else {
            return null;
        }
    }

    @Override
    public E set(int index, Object element) {
        if (index >= 0 && index < this.size) {
            elementData[index] = element;
            return (E) elementData[index];
        } else {
            return null;
        }
    }


    @Override
    public void replace(int index, Object element) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"elementData\":")
                .append(Arrays.toString(elementData));
        sb.append(",\"size\":")
                .append(size);
        sb.append('}');
        return sb.toString();
    }
}
