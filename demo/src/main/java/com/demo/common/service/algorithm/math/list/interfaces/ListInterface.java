package com.demo.common.service.algorithm.math.list.interfaces;

/**
 * 顺序表接口
 *
 * @param <E>
 */
public interface ListInterface<E> {

    int size();

    boolean isEmpty();

    boolean add(E e);

    void add(int index, E element);

    boolean remove(Object o);

    E remove(int index);

    E get(int index);

    E set(int index, E element);

    void replace(int index, E element);

    void clear();

    int indexOf(Object o);

    int lastIndexOf(Object o);

    boolean contains(Object o);
}
