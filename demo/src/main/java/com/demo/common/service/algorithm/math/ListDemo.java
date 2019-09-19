package com.demo.common.service.algorithm.math;

import com.demo.common.service.algorithm.math.list.DoublyCircularLinkedList;
import com.demo.common.service.algorithm.math.list.DoublyLinkedList;
import com.demo.common.service.algorithm.math.list.MSingleyLinkedList;
import com.demo.common.service.algorithm.math.list.MyArrayList;
import com.demo.common.service.algorithm.math.list.interfaces.DequeInterface;
import com.demo.common.service.algorithm.math.list.interfaces.ListInterface;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {
    @Test
    public void testArrayList() {
        List<Object> list = new ArrayList<>();
        list.add(12);
        list.add("aa");
        list.add(11.22);
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list);
    }

    @Test
    public void testLinkedList() {
        List<Object> list = new LinkedList<>();
        list.add(12);
        list.add("aa");
        list.add(11.22);
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list);
    }

    @Test
    public void testMyArrayList() {
        operation(new MyArrayList<>());
    }

    @Test
    public void testMyLinkedList() {
        operation(new MSingleyLinkedList<>());
    }

    @Test
    public void testMyDoublyLinkedList() {
        operation(new DoublyLinkedList<>());
    }

    @Test
    public void testMyDoublyCircularLinkedList() {
        operation(new DoublyCircularLinkedList<>());
    }

    private void operation(ListInterface<Object> list){
        list.add("a");
        list.add("ab");
        list.add("abc");
        list.add(1, 666);
        list.add(0, 777);
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list);
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(1, 999);
        System.out.println(list);
        System.out.println(list.remove(0));
        System.out.println(list);
        System.out.println(list.remove(6));
        System.out.println(list);
    }

    private void operation(DequeInterface<Object> list){
        list.addFirst("ab");
        list.addFirst("abc");
        list.addLast("123");
        list.addLast("12");
        list.addFirst("kkk1");
        list.addFirst("k1");
        list.addLast("kkk2");
        list.addLast("k2");
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list);
        System.out.println(list.removeFirst());
        System.out.println(list);
        System.out.println(list.removeLast());
        System.out.println(list);
        System.out.println("___________________________________");
        System.out.println(list.getFirstNode().prev());
        System.out.println(list.getFirstNode());
        System.out.println(list.getFirstNode().next());
        System.out.println(list.getFirstNode().next().next());
        System.out.println(list.getFirstNode().next().next().next());
        System.out.println(list.getFirstNode().next().next().next().next());
        System.out.println("____________________");
        System.out.println(list.getLastNode().prev().prev().prev().prev());
        System.out.println(list.getLastNode().prev().prev().prev());
        System.out.println(list.getLastNode().prev().prev());
        System.out.println(list.getLastNode().prev());
        System.out.println(list.getLastNode());
        System.out.println(list.getLastNode().next());
        System.out.println("___________________________________");
        System.out.println(list);
        System.out.println(list.removeFirst());
        System.out.println(list.removeLast());
        System.out.println(list.removeFirst());
        System.out.println(list.removeLast());
        System.out.println(list);
        System.out.println(list.removeFirst());
        System.out.println(list.removeLast());
        System.out.println(list);
    }

    /**
     * Deque
     * <p>
     * 使用 栈 实现 进制转换
     */
    @Test
    public void test() {
        int number = 35;

        Deque stack = new LinkedList();
        do {
            stack.push(number % 2);
            number = number / 2;
        } while (0 < number);

        List list = new ArrayList();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        System.out.println(list);
    }
}
