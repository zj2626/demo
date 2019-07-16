package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree.node;

/**
 * 二叉链表的结点
 *
 * @param <E>
 */
public class Node<E> {
    private E data;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public Node() {
    }

    public Node(E data) {
        this.data = data;
    }

    public Node(E data, Node<E> leftChild, Node<E> rightChild) {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public boolean hasLeftChild() {
        return null != leftChild;
    }

    public boolean hasRightChild() {
        return null != rightChild;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<E> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<E> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<E> rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "<" + data.toString() + " - " + this.hashCode() + ">";
    }
}
