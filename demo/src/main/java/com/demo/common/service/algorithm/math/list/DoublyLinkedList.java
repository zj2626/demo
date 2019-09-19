package com.demo.common.service.algorithm.math.list;

import com.demo.common.service.algorithm.math.list.interfaces.DequeInterface;
import com.demo.common.service.algorithm.math.list.node.DoublyNode;

/**
 * 线性表:
 * 双向链表 待完成
 */
public class DoublyLinkedList<E> implements DequeInterface<E> {
    private DoublyNode<E> first;   //头节点 存数据
    private DoublyNode<E> last;    //尾节点 存数据
    private int size;              //元素的个数

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
    public void addFirst(E e) {
        // 新建结点
        DoublyNode<E> newNode = new DoublyNode<>(e, null, null);

        if (first == null) {
            // 直接就是first
            first = newNode;
            last = newNode;
        } else {
            // 新结点指向当前first
            newNode.setNext(first);
            first.setPrev(newNode);
            first = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        // 新建结点
        DoublyNode<E> newNode = new DoublyNode<>(e, null, null);

        if (last == null) {
            // 直接就是last
            first = newNode;
            last = newNode;
        } else {
            // last指向新结点
            newNode.setPrev(last);
            last.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        DoublyNode<E> node = first;
        if (node != null) {
            DoublyNode<E> nextNode = node.next();
            if (nextNode == null) {
                last = null;
            } else {
                nextNode.setPrev(null);
                node.setNext(null);
            }
            first = nextNode;

            size--;
        }

        return node.getData();
    }

    @Override
    public E removeLast() {
        DoublyNode<E> node = last;
        if (node != null) {
            DoublyNode<E> prevNode = node.prev();
            if (prevNode == null) {
                first = null;
            } else {
                prevNode.setNext(null);
                node.setPrev(null);
            }
            last = prevNode;

            size--;
        }

        return node.getData();
    }

    @Override
    public E pollFirst() {
        return removeFirst();
    }

    @Override
    public E pollLast() {
        return removeLast();
    }

    @Override
    public E getFirst() {
        return first == null ? null : first.getData();
    }

    @Override
    public E getLast() {
        return last == null ? null : last.getData();
    }

    @Override
    public E peekFirst() {
        return getFirst();
    }

    @Override
    public E peekLast() {
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E poll() {
        return first == null ? null : first.getData();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public DoublyNode<E> getFirstNode() {
        return first;
    }

    @Override
    public DoublyNode<E> getLastNode() {
        return last;
    }

    @Override
    public String toString() {
        return "DoublyLinkedList{" +
                "data=" + elementStr() +
                ", head=" + first +
                ", tail=" + last +
                ", size=" + size +
                '}';
    }

    private String elementStr() {
        StringBuffer string = new StringBuffer("[");

        DoublyNode<E> node = first;
        if (null != node) {
            while (true) {
                string.append(node.getData());
                if (node.hasNext()) {
                    string.append(", ");
                }

                if (node.hasNext()) {
                    node = node.next();
                } else {
                    break;
                }
            }
        }

        string.append("]");
        return string.toString();
    }
}