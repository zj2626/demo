package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree.interfaces.BinaryTreeInterface;
import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.tree.node.Node;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MySimpleBinaryTree<E> implements BinaryTreeInterface<E> {
    private Node<E> root;

    @Override
    public int size() {
        if (!isEmpty()) {
            return size(root);
        } else {
            return 0;
        }
    }

    /*递归求个数*/
    private int size(Node<E> node) {
        int size = 1;

        // 左子树遍历
        if (node.hasLeftChild()) {
            size += size(node.getLeftChild());
        }

        // 右子树遍历
        if (node.hasRightChild()) {
            size += size(node.getRightChild());
        }

        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public E root() {
        return root.getData();
    }

    @Override
    public void root(Node<E> node) {
        root = node;
    }

    @Override
    public int height() {
        if (!isEmpty()) {
            return height(root);
        } else {
            return 0;
        }
    }

    /*递归求高度*/
    private int height(Node<E> node) {
        int hLeft = 1;
        int hRight = 1;

        // 左子树遍历
        if (node.hasLeftChild()) {
            hLeft += height(node.getLeftChild());
        }

        // 右子树遍历
        if (node.hasRightChild()) {
            hRight += height(node.getRightChild());
        }

        return hLeft > hRight ? hLeft : hRight;
    }

    @Override
    public Node<E> get(E element) {
        if (!isEmpty()) {
            return get(root, element);
        } else {
            return null;
        }
    }

    /*递归求元素*/
    private Node<E> get(Node<E> node, E element) {
        if (node.getData().equals(element)) {
            return node;
        }

        Node<E> rLeft = null;
        Node<E> rRight = null;

        // 左子树遍历
        if (node.hasLeftChild()) {
            rLeft = get(node.getLeftChild(), element);
        }

        // 右子树遍历
        if (node.hasRightChild()) {
            rRight = get(node.getRightChild(), element);
        }

        return rLeft != null ? rLeft : rRight;
    }

    /*先序遍历*/
    @Override
    public void preOrderTraversal() {
        if (!isEmpty()) {
            preOrderTraversal(root);
        }
        System.out.println("先序遍历");
    }

    private void preOrderTraversal(Node<E> node) {
        // 输出根节点
        System.out.print(node.getData().toString() + '\t');

        // 左子树遍历
        if (node.hasLeftChild()) {
            preOrderTraversal(node.getLeftChild());
        }

        // 右子树遍历
        if (node.hasRightChild()) {
            preOrderTraversal(node.getRightChild());
        }
    }

    /*中序遍历*/
    @Override
    public void intermediateTraversal() {
        if (!isEmpty()) {
            intermediateTraversal(root);
        }
        System.out.println("中序遍历");
    }

    private void intermediateTraversal(Node<E> node) {
        // 左子树遍历
        if (node.hasLeftChild()) {
            intermediateTraversal(node.getLeftChild());
        }

        // 输出根节点
        System.out.print(node.getData().toString() + '\t');

        // 右子树遍历
        if (node.hasRightChild()) {
            intermediateTraversal(node.getRightChild());
        }
    }

    /*后序遍历*/
    @Override
    public void postOrderTraversal() {
        if (!isEmpty()) {
            postOrderTraversal(root);
        }
        System.out.println("后序遍历");
    }

    private void postOrderTraversal(Node<E> node) {
        // 左子树遍历
        if (node.hasLeftChild()) {
            postOrderTraversal(node.getLeftChild());
        }

        // 右子树遍历
        if (node.hasRightChild()) {
            postOrderTraversal(node.getRightChild());
        }

        // 输出根节点
        System.out.print(node.getData().toString() + '\t');
    }

    /*先序遍历 - 非递归 - 使用Deque(当做栈)实现*/
    @Override
    public void preOrderTraversalByStack() {
        if (!isEmpty()) {
            Deque<Node<E>> deque = new LinkedList<>();
            deque.push(root);

            while (deque.size() > 0) {
                // 先根节点遍历 所以先把当前树根节点出栈
                Node<E> node = deque.pop();
                System.out.print(node.getData().toString() + '\t');

                // 每次结点出栈,都检查其是否有左右子结点, 有则把当前结点的左右子结点入栈
                if (node.hasRightChild()) {
                    deque.push(node.getRightChild());
                }
                if (node.hasLeftChild()) {
                    deque.push(node.getLeftChild());
                }
            }
        }
        System.out.println("先序遍历");
    }

    /*中序遍历 - 非递归 - 使用Deque(当做栈)实现*/
    @Override
    public void intermediateTraversalByStack() {
        if (!isEmpty()) {
            Deque<Node<E>> deque = new LinkedList<>();
            // current永远指向待遍历的结点或者null
            Node<E> current = root;

            // !deque.isEmpty()表示没有遍历完所有结点 current表示上一个遍历的结点没有右子结点
            while (!deque.isEmpty() || current != null) {
                // 把current结点及其左子结点(如果存在的话)及其左子结点的左子结点...入栈
                // current==null表示已经没有左子结点了
                while (current != null) {
                    deque.push(current);
                    current = current.getLeftChild();
                }

                if (!deque.isEmpty()) {
                    // 出栈 并寻找其右子结点,如果存在就会执行上面while寻找该右子结点的左子结点...如果没有就继续在这里出栈下一个(因为上面while的判断条件current != null)
                    current = deque.pop();
                    System.out.print(current.getData().toString() + '\t');
                    current = current.getRightChild();
                }
            }
        }
        System.out.println("中序遍历");
    }

    /*后序遍历 - 非递归 - 使用Deque(当做栈)实现*/
    @Override
    public void postOrderTraversalByStack() {
        if (!isEmpty()) {

        }
        System.out.println("后序遍历");
    }

    /*层次遍历 使用队列实现*/
    @Override
    public void levelOrderTraversal() {
        if (!isEmpty()) {
            Queue<Node<E>> queue = new LinkedList<>();
            queue.add(root);

            while (queue.size() > 0) {
                for (int i = 0; i < queue.size(); i++) {
                    Node<E> node = queue.poll();
                    System.out.print(node.getData().toString() + '\t');

                    if (node.hasLeftChild()) {
                        queue.add(node.getLeftChild());
                    }
                    if (node.hasRightChild()) {
                        queue.add(node.getRightChild());
                    }
                }
            }
        }
        System.out.println("层次遍历");
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }
}
