package com.demo.common.service.thread.CASandAQS;

/**
 * AQS: AbstractQueuedSynchronizer
 * <p>
 * AQS(AbstractQueuedSynchronizer)，AQS是JDK下提供的一套用于实现基于FIFO等待队列的阻塞锁和相关的同步器的一个同步框架。
 * 它使用了一个原子的int value status来作为同步器的状态（如：独占锁，1代表已占有，0代表未占有），
 * 通过该类提供的原子修改status方法（getState setState and compareAnsSetState），我们可以把它作为同步器的基础框架类来实现各种同步器。
 * AQS还定义了一个实现了Condition接口的ConditionObject内部类。
 * Condition 将 Object 监视器方法（wait、notify 和 notifyAll）分解成截然不同的对象，
 * 以便通过将这些对象与任意 Lock 实现组合使用，为每个对象提供多个等待 set （wait-set）。
 * 其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
 * <p>
 * 简单来说，就是Condition提供类似于Object的wait、notify的功能signal和await，
 * 都是可以使一个正在执行的线程挂起（推迟执行），直到被其他线程唤醒。但是Condition更加强大，
 * 如支持多个条件谓词、保证线程唤醒的顺序和在挂起时不需要拥有锁。
 * 这个抽象类被设计为作为一些可用原子int值来表示状态的同步器的基类。
 * 如果你有看过类似 CountDownLatch 类的源码实现，会发现其内部有一个继承了 AbstractQueuedSynchronizer 的内部类 Sync。
 * 可见 CountDownLatch 是基于AQS框架来实现的一个同步器.类似的同步器在JUC下还有不少。(eg. Semaphore)
 * <p>
 * AQS管理一个关于状态信息的单一整数，该整数可以表现任何状态。比如，
 * Semaphore 用它来表现剩余的许可数；
 * ReentrantLock 用它来表现拥有它的线程已经请求了多少次锁；
 * FutureTask 用它来表现任务的状态(尚未开始、运行、完成和取消)；
 * <p>
 * 提供两种锁: 独占锁, 共享锁
 * 1. 独占锁:ReentrantReadWriteLock.WriteLock
 * 2. 共享锁:ReentrantReadWriteLock.ReadLock, CountDownLatch
 */
public class AQSDemo {
}
