package com.demo.common.service.thread.queue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    /**
     * ArrayBlockingQueue
     * DelayQueue
     * DelayedWorkQueue
     * LinkedBlockingQueue
     * LinkedBlockingDeque
     * LinkedTransferQueue
     * PriorityBlockingQueue
     * SynchronousQueue
     * <p>
     * ConcurrentLinkedQueue
     * ConcurrentLinkedDeque
     * <p>
     * peek
     * element
     * put:     如果队列满了，一直阻塞，直到队列不满了或者线程被中断-->阻塞
     * offer:   如果队列没满，立即返回true； 如果队列满了，立即返回false-->不阻塞
     * offer(有timeout):   在队尾插入一个元素,，如果队列已满，则进入等待，直到出现以下三种情况：-->阻塞
     * * 被唤醒
     * * 等待时间超时
     * * 当前线程被中断
     * take:    如果队列空了，一直阻塞，直到队列不为空或者线程被中断-->阻塞
     * poll:    如果没有元素，直接返回null；如果有元素，出队-->不阻塞
     * poll(有timeout):  如果队列不空，出队；如果队列已空且已经超时，返回null；如果队列已空且时间未超时，则进入等待，直到出现以下三种情况：
     * * 被唤醒
     * * 等待时间超时
     * * 当前线程被中断
     * remove:   调用的还是poll()方法, 没有元素则抛异常
     * remove(object):   LinkedBlockingQueue中该方法同时需要两把锁
     * <p>
     * 特点:
     * BlockingQueue队列中不能包含null元素；
     * BlockingQueue接口的实现类都必须是线程安全的，实现类一般通过“锁”保证线程安全；
     * BlockingQueue 可以是限定容量的。remainingCapacity()方法用于返回剩余可用容量，对于没有容量限制的BlockingQueue实现，该方法总是返回Integer.MAX_VALUE 。
     * <p>
     *     Queue:队列(queue)是一种常用的数据结构，可以将队列看做是一种特殊的线性表，该结构遵循的先进先出原则。Java中，LinkedList实现了Queue接口,因为LinkedList进行插入、删除操作效率较高
     *     Deque:双向队列(Deque),是Queue的一个子接口，双向队列是指该队列两端的元素既能入队(offer)也能出队(poll),如果将Deque限制为只能从一端入队和出队，则可实现栈的数据结构。对于栈而言，有入栈(push)和出栈(pop)，遵循先进后出原则
     * <p>
     * 操作类型     抛出异常	    返回特殊值	阻塞线程	    超时
     * 插入	        add(e)	    offer(e)	put(e)	    offer(e, time, unit)
     * 删除	        remove()	poll()	    take()	    poll(time, unit)
     * 读取	        element()	peek()	    /	        /
     */
    @Test
    public void test() throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(30);

        blockingQueue.put("first ");
        blockingQueue.put("ab ");
        blockingQueue.offer("cd ");
        blockingQueue.offer("ef ", 3, TimeUnit.SECONDS);
        blockingQueue.put("gh ");
        System.out.println(blockingQueue.size());
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.peek());
        System.out.println("----------------");
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.size());
        System.out.println("----------------");
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.remove("aka"));
        try {
            System.out.println(blockingQueue.remove());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
