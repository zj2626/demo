package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree.interfaces;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree.node.Node;

/**
 * 二叉树接口
 */
public interface BinaryTreeInterface<E> {
    int size();

    boolean isEmpty();

    int height();

    E root();

    void root(Node<E> node);

    Node<E> get(E element);

    /*先序遍历*/
    void preOrderTraversal();

    /*中序遍历*/
    void intermediateTraversal();

    /*后序遍历*/
    void postOrderTraversal();

    /*先序遍历 - 非递归*/
    void preOrderTraversalByStack();

    /*中序遍历 - 非递归*/
    void intermediateTraversalByStack();

    /*后序遍历 - 非递归*/
    void postOrderTraversalByStack();

    /*层次遍历*/
    void levelOrderTraversal();
}
