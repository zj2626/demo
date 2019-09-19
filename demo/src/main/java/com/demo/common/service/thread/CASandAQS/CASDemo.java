package com.demo.common.service.thread.CASandAQS;

/**
 * CAS: Compare And Swap
 * <p>
 * CAS(Compare And Swap)，即比较并交换。是解决多线程并行情况下使用锁造成性能损耗的一种机制，
 * CAS操作包含三个操作数——内存位置（V）、预期原值（A）和新值(B)。如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值。
 * 否则，处理器不做任何操作。无论哪种情况，它都会在CAS指令之前返回该位置的值。
 * CAS有效地说明了“我认为位置V应该包含值A；如果包含该值，则将B放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可。
 * <p>
 * 在JAVA中，sun.misc.Unsafe 类提供了硬件级别的原子操作来实现这个CAS。
 * java.util.concurrent 包下的大量类都使用了这个 Unsafe.java 类的CAS操作。
 * <p>
 * java.util.concurrent.atomic 包下的类大多是使用CAS操作来实现的(eg. AtomicInteger.java,AtomicBoolean,AtomicLong)
 * <p>
 * <p>
 * *    for (;;) {
 * *         int current = get();
 * *          if (compareAndSet(current, newValue))
 * *             return current;
 * *    }
 * <p>
 * 一般来说在竞争不是特别激烈的时候，使用该包下的原子操作性能比使用 synchronized 关键字的方式高效的多(查看getAndSet()，
 * 可知如果资源竞争十分激烈的话，这个for循环可能换持续很久都不能成功跳出。不过这种情况可能需要考虑降低资源竞争才是)。
 */
public class CASDemo {
}
