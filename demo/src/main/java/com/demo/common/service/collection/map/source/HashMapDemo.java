package com.demo.common.service.collection.map.source;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @see HashMap
 */
public class HashMapDemo<K, V> {

    // 默认初始容量 16 (必须是2的倍数)
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    // map最大容量
    static final int MAXIMUM_CAPACITY = 1 << 30;

    // 默认负载系数 0.75
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    static final int TREEIFY_THRESHOLD = 8;

    static final int UNTREEIFY_THRESHOLD = 6;

    static final int MIN_TREEIFY_CAPACITY = 64;

    transient MyNode<K, V>[] table;

    transient Set<Map.Entry<K, V>> entrySet;

    transient int size;

    transient int modCount;

    // 下一个要调整大小的大小值, 必定是2的倍数
    int threshold;

    // 当前haspmap的负载系数
    final float loadFactor;

    /**
     * 默认构造方法不创建存储空间 需要到第一次put时才初始化
     *
     * @see HashMap#HashMap()
     */
    public HashMapDemo() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    public HashMapDemo(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 设置初始值, 不创建初始容量
     *
     * @see HashMap#HashMap(int, float)
     */
    public HashMapDemo(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        // 容量最大不能超过 MAXIMUM_CAPACITY
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        // 负载系数必须大于0且为浮点数
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public int size() {
        return size;
    }

    /**
     * 计算hash值
     *
     * @see HashMap#hash(java.lang.Object)
     */
    static final int hash(Object key) {
        int h;
        // 让哈希值的低16位与高6位异或, 使哈希值更随机
        // 由于和（length-1）运算，length 绝大多数情况小于2的16次方。所以始终是hashcode 的低16位（甚至更低）参与运算。要是高16位也参与运算，会让得到的下标更加散列。
        // 所以这样高16位是用不到的，如何让高16也参与运算呢。所以才有hash(Object key)方法。让他的hashCode()和自己的高16位^运算。所以(h >>> 16)得到他的高16位与hashCode()进行^运算。
        // &和|都会使得结果偏向0或者1 ,并不是均匀的概念,所以用^
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * @see HashMap#get(java.lang.Object)
     */
    public V get(Object key) {
        MyNode<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * @see HashMap#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * @see HashMap#getNode(int, java.lang.Object)
     */
    final MyNode<K, V> getNode(int hash, Object key) {
        MyNode<K, V>[] tab;
        MyNode<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof MyTreeNode)
                    return ((MyTreeNode<K, V>) first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**
     * @see HashMap#put(java.lang.Object, java.lang.Object)
     */
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * @see HashMap#putVal(int, java.lang.Object, java.lang.Object, boolean, boolean)
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        System.out.println("do putVal");

        // tab就是当前map中的table的引用
        MyNode<K, V>[] tab;
        // p是要存放数据的数组位置(数组位置同时也是链表的头)
        MyNode<K, V> p;
        // n是当前map容量 i是要存放数据的位置下标
        int n, i;

        // 第一次put数据, 需要进行初始化空间
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;

        // put数据中,n是已经初始化的数组空间大小(table.length), 通过[取余]得到本次插入位置
        // 判断当前位置是否已经有值
        if ((p = tab[i = (n - 1) & hash]) == null)
            // 没有值就直接插
            tab[i] = newNode(hash, key, value, null);
        else {
            // 有值就要拉链表
            // 真正要插入的位置
            MyNode<K, V> e;
            // 数据的key
            K k;

            // 判断当前位置(数组上的节点)已经存在的key和要插入的key是否相同, 是就设置e为当前位置节点(为后面覆盖当前位置的数据)
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
                // 如果当前节点已经转换为红黑树, 则进行红黑树处理
            else if (p instanceof MyTreeNode)
                e = ((MyTreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
                // 都不是, 那就要访问当前节点的拉链,一个一个对比, 如果长度(节点个数)超过TREEIFY_THRESHOLD, 就要把当前节点类型改为红黑树
            else {
                // 已经存在的节点个数 == binCount+1
                for (int binCount = 0; ; ++binCount) {
                    // 设置e是p的下一个
                    // 如果不存在下一个节点, 那就说明要么没有拉链, 要么就已经循环到最后了, 就新建节点直接链接到p的后面, 新建节点, 跳出循环
                    if ((e = p.next) == null) {
                        // 数据连接当前节点后面
                        p.next = newNode(hash, key, value, null);
                        // 判断当前链表节点个数是否大于TREEIFY_THRESHOLD(8, 意思是当前put节点是链表的第9个元素), 大于则改为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) { // -1 for 1st
                            System.out.println(">>>>>>>>>>>>>>>> 链表转为红黑树: 链表: \n" + this.toString() + "\n<<<<<<<<<<<<<<<< 链表转为红黑树");
                            treeifyBin(tab, hash);
                        }

                        break;
                    }
                    // 如果存在下一个节点, 就和上面判断相同, 判断已经存在的key和要插入的key是否相同, 如果相同则跳出循环, 后面代码操作:覆盖原有数据
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;

                    // 设置p为下一个节点 然后继续循环
                    p = e;
                }
            }

            // 覆盖原有数据
            if (e != null) {
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    // 覆盖
                    e.value = value;
                // 空方法 可覆盖
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        // 如果插入数据个数已经大于下一个要调整大小(当前数组总大小*0.75)的大小值, 就会resize
        if (++size > threshold)
             resize();
        afterNodeInsertion(evict);
        return null;
    }

    /**
     * 初始化或增加表大小, 每次增加当前数组总大小的二倍
     *
     * @see HashMap#resize()
     */
    final MyNode<K, V>[] resize() {
        System.out.println(this);
        System.out.println("do resize... 当前数据个数:" + size + " 调整大小界限:" + threshold);

        // 旧table
        MyNode<K, V>[] oldTab = table;
        // 旧table的容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        // 旧调整临界点
        int oldThr = threshold;

        // 新的
        int newCap, newThr = 0;

        // 如果map中已经初始化过存储空间
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }

        // 如果map中没初始化过存储空间, 但是存在调整临界点(调用带有initialCapacity入参的构造方法)
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;

            // 如果map中没存储空间, 进行初始化, 默认大小:16, 默认整临界点: 16*0.75
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        // 如果整临界点没有初始化, 就使用新的table容量计算一次
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;

        // 开辟新的存储空间 新table
        @SuppressWarnings({"rawtypes", "unchecked"})
        MyNode<K, V>[] newTab = (MyNode<K, V>[]) new MyNode[newCap];
        table = newTab;

        // 如果有旧空间, 循环每个元素位置,重新计算hash然后存放到新table的固定位置上
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                // e存储当前循环要复制的数据
                MyNode<K, V> e;
                // 位置有数据
                if ((e = oldTab[j]) != null) {
                    // 旧table该位置置空
                    oldTab[j] = null;

                    // 判断当前节点没有链表, 就直接复制到新table
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;

                    // 如果当前节点已经转换为红黑树, 则进行红黑树处理
                    else if (e instanceof MyTreeNode)
                        ((MyTreeNode<K, V>) e).split(this, newTab, j, oldCap);

                    // 都不是, 那就要访问当前节点的拉链 TODO
                    else { // preserve order
                        MyNode<K, V> loHead = null, loTail = null;
                        MyNode<K, V> hiHead = null, hiTail = null;
                        // 链表的下一个节点
                        MyNode<K, V> next;
                        do {
                            next = e.next;
                            /*
                             * 判断是否需要移动位置 (&:如果相对应位都是1，则结果为1，否则为0), (e.hash & oldCap) == 0 则不需要移动
                             * -------------------------------------------------------------------------------------
                             * resize前: hash & oldCap-1 也就是 19 & 8-1 ---> 3
                             * 0001 0011
                             * 0000 0111 --> 0000 0011
                             * resize前检测: hash & oldCap 也就是 19 & 8 ---> 0 (= 0) 不需要移动
                             * 0000 0011
                             * 0000 1000 --> 0000 0000
                             * resize后: hash & newCap-1 也就是  19 & 16-1 ---> 3 (可通过上面 hash & oldCap == 0 检测)
                             * 0001 0011
                             * 0000 1111 --> 0000 0011
                             * -------------------------------------------------------------------------------------
                             * resize前: hash & oldCap-1 也就是 59 & 8-1 ---> 3
                             * 0011 1011
                             * 0000 0111 --> 0000 0011
                             * resize前检测: hash & oldCap 也就是 19 & 8 ---> 8 (> 0) 需要移动
                             * 0011 1011
                             * 0000 1000 --> 0000 1000
                             * resize后: hash & newCap-1 也就是  19 & 16-1 ---> 11
                             * 0011 1011
                             * 0000 1111 --> 0000 1011
                             * -------------------------------------------------------------------------------------
                             */
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        System.out.println(this);
        System.out.println("---------------------------------------------------------------------------");
        return newTab;
    }

    /**
     * 当前链表转换为红黑树 TODO
     *
     * @see HashMap#treeifyBin(java.util.HashMap.Node[], int)
     */
    final void treeifyBin(MyNode<K, V>[] tab, int hash) {
        int n, index;
        MyNode<K, V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            MyTreeNode<K, V> hd = null, tl = null;
            do {
                MyTreeNode<K, V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }

    MyNode<K, V> newNode(int hash, K key, V value, MyNode<K, V> next) {
        return new MyNode<>(hash, key, value, next);
    }

    MyTreeNode<K, V> replacementTreeNode(MyNode<K, V> p, MyNode<K, V> next) {
        return new MyTreeNode<>(p.hash, p.key, p.value, next);
    }

    void afterNodeAccess(MyNode<K, V> p) { }

    void afterNodeInsertion(boolean evict) { }

    void afterNodeRemoval(MyNode<K, V> p) { }

    @Override
    public String toString() {
        return "HashMapDemo{" +
                " size=" + size +
                " length=" + (null == table ? null : table.length) +
                " threshold=" + threshold +
                " table=" + (null == table ? null : print(table)) +
                '}';
    }

    private String print(MyNode<K, V>[] table) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            result.append("\n\t").append(String.format("%3d", i)).append(" -> ").append(table[i]);
        }
        result.append("\n\t");
        return result.toString();
    }
}
