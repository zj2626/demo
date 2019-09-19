package com.demo.common.service.thread;

/**
 * 没有volatile的写法看似解决了同步问题，但是有个很大的隐患。实例化对象的那行代码（标记为info的那行），实际上可以分解成以下三个步骤：
 *
 *     分配内存空间
 *     初始化对象
 *     将对象指向刚分配的内存空间
 *
 * 但是有些编译器为了性能的原因，可能会将第二步和第三步进行重排序，顺序就成了：
 *
 *     分配内存空间
 *     将对象指向刚分配的内存空间
 *     初始化对象
 *
 *
 *
 * ** 为了解决上述问题，需要在uniqueSingleton前加入关键字volatile。使用了volatile关键字后，重排序被禁止，所有的写（write）操作都将发生在读（read）操作之前。
 */
public class DoubleCheck {
    private volatile static DoubleCheck doubleCheck;

    private DoubleCheck(){}

    public static DoubleCheck getInstance(){
        if(null == doubleCheck){
            synchronized (DoubleCheck.class){
                if(null == doubleCheck){
                    doubleCheck = new DoubleCheck();  // info
                }
            }
        }

        return doubleCheck;
    }

    public static void main(String[] args) {
        DoubleCheck doubleCheck = DoubleCheck.getInstance();
        System.out.println(doubleCheck);
    }
}
