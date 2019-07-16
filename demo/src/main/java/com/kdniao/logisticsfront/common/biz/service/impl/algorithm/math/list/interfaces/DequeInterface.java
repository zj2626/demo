package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.node.DoublyNode;

/**
 * 双向链表接口
 *
 * @param <E>
 */
public interface DequeInterface<E> {
    int size();

    boolean isEmpty();

    void addFirst(E e);

    void addLast(E e);

    boolean offerFirst(E e);

    boolean offerLast(E e);

    E removeFirst();

    E removeLast();

    E pollFirst();

    E pollLast();

    E getFirst();

    E getLast();

    E peekFirst();

    E peekLast();

    boolean removeFirstOccurrence(Object o);

    boolean removeLastOccurrence(Object o);

    boolean add(E e);

    boolean offer(E e);

    E element();

    E poll();

    void push(E e);

    boolean remove(Object o);

    boolean contains(Object o);

    /*自定义特殊方法 为了验证链表正确性*/
    DoublyNode<E> getFirstNode();

    DoublyNode<E> getLastNode();
}
