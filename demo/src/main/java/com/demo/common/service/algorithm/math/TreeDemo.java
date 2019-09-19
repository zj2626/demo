package com.demo.common.service.algorithm.math;

import com.demo.common.service.algorithm.math.tree.MySimpleBinaryTree;
import com.demo.common.service.algorithm.math.tree.interfaces.BinaryTreeInterface;
import com.demo.common.service.algorithm.math.tree.node.Node;
import org.apache.commons.collections.list.TreeList;
import org.junit.Test;

import java.util.List;

public class TreeDemo {
    @Test
    public void testTreeList() {
        List list = new TreeList();
        list.add(12);
        list.add("aa");
        list.add(11.22);
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list);
    }

    @Test
    public void testMyTreeList() {
        operation(new MySimpleBinaryTree<>());
    }

    private void operation(BinaryTreeInterface<Object> tree) {
        Node node7 = new Node<>(7);
        Node node6 = new Node<>(6, null, node7);
        Node node3 = new Node<>(3);
        Node node2 = new Node<>(2, node3, node6);
        Node node5 = new Node<>(5);
        Node node4 = new Node<>(4, null, node5);
        Node node1 = new Node<>(1, node4, node2);
        tree.root(node1);
        System.out.println(tree.isEmpty());
        System.out.println(tree.height());
        System.out.println(tree.size());

        /*先序遍历  1	4	5	2	3	6	7*/
        tree.preOrderTraversal();
        /*中序遍历  4	5	1	3	2	6	7*/
        tree.intermediateTraversal();
        /*后序遍历  5	4	3	7	6	2	1*/
        tree.postOrderTraversal();
        /*层次遍历  1	4	2	5	3	6	7*/
        tree.levelOrderTraversal();
        System.out.println();

        tree.preOrderTraversalByStack();
        tree.intermediateTraversalByStack();
        tree.postOrderTraversalByStack();
        System.out.println("____________________________");

        System.out.println(tree.get(7));
        System.out.println(tree.get(2));
        System.out.println(tree.get(1));
    }
}
