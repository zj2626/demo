package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces.ListInterface;

import java.util.Arrays;

/**
 * 线性表:
 * 顺序表，可扩容
 */
public class MyArrayList<E> implements ListInterface<E> {
    private Object[] elementData;   //底层是数组
    private int size;               //元素的个数

    public MyArrayList() {
        elementData = new Object[4];
    }

    public MyArrayList(int initSize) {
        elementData = new Object[initSize];
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
        this.add(size, o);
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

        for (int i = this.size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = element;
        size++;
    }

    private void ensureCapacityInternal() {
        if (this.size >= elementData.length - 1) {
//            Object[] newElementData = new Object[size + 5];
//            for (int i = 0; i < size; i++) {
//                newElementData[i] = elementData[i];
//            }
//            elementData = newElementData;

            /*同理*/
            elementData = Arrays.copyOf(elementData, size + size / 2);

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
                .append(elementStr());
        sb.append(",\"size\":")
                .append(size);
        sb.append('}');
        return sb.toString();
    }

    private String elementStr() {
        StringBuffer string = new StringBuffer("[");

        for (int i = 0; i < this.size; i++) {
            string.append(elementData[i]);
            if (i != this.size - 1) {
                string.append(", ");
            }
        }

        string.append("]");
        return string.toString();
    }
}
