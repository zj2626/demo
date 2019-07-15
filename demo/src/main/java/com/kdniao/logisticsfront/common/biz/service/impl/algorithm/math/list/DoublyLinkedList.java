package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces.ListInterface;
import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.node.DoubleNode;

/**
 * 线性表:
 * 双向链表 待完成
 */
public class DoublyLinkedList<E> implements ListInterface<E> {
    private DoubleNode<E> head = new DoubleNode<>();    //头节点 不存数据 为了编程方便
    private int size;                       //元素的个数

    public DoublyLinkedList() {

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
    public boolean add(E e) {
        this.add(size, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        // 新创建一个节点
        DoubleNode<E> newNode = new DoubleNode<>(element, null);

        DoubleNode<E> node = cycle(index);

        // 找到待插入节点的后继
        DoubleNode<E> nextNode = node.next();
        // 指定新节点的后继
        newNode.setNext(nextNode);
        // 指定新节点的后继
        node.setNext(newNode);
        size++;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E remove(int index) {
        if (index >= 0 && index < this.size) {
            // 得到删除节点的前驱节点
            DoubleNode<E> node = cycle(index);

            // 得到待删除节点
            DoubleNode<E> nextNode = node.next();
            DoubleNode<E> delnode = nextNode;

            // 得到待删除节点后继节点
            nextNode = nextNode.next();
            delnode.setNext(null);

            // 设置待删除节点前驱节点 指向待删除节点后继节点
            node.setNext(nextNode);
            size--;
            return delnode.getData();
        } else {
            return null;
        }
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < this.size) {
            DoubleNode<E> node = cycle(index + 1);

            return node.getData();
        } else {
            return null;
        }
    }

    @Override
    public E set(int index, E element) {
        if (index >= 0 && index < this.size) {
            DoubleNode<E> node = cycle(index + 1);
            node.setData(element);

            return node.getData();
        } else {
            return null;
        }
    }

    private DoubleNode<E> cycle(int index) {
        DoubleNode<E> node = head;
        int i = 0;
        while (node.hasNext() && i < index) {
            node = node.next();
            i++;
        }
        return node;
    }

    @Override
    public void replace(int index, E element) {

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
        return "MyLinkedList{" +
                "data=" + elementStr() +
                ", size=" + size +
                '}';
    }

    private String elementStr() {
        StringBuffer string = new StringBuffer("[");

        DoubleNode<E> node = head;
        if (null != node) {
            while (node.hasNext()) {
                node = node.next();
                string.append(node.getData());
                if (node.hasNext()) {
                    string.append(",");
                }
            }
        }

        string.append("]");
        return string.toString();
    }
}