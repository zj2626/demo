---
title: 4.OutOfMemoryError

comments: true    

tags: 
    - 深入了解java虚拟机
    - java

categories: 
    - java虚拟机

description: 

---

## 1. 溢出

虚拟机规范中, 除了程序计数器, 其他部分都可能发生OOM异常

## 2.溢出种类



### 1.Java堆溢出

> java堆存储对象实例, 只需不断创建对象且GC Roots到对象可达避免垃圾回收, 达到最大堆限制即溢出

```java
/**
 *  vm args: -Xms20m -Xmx20m -XX:+PrintGCDetails
 *  @see com.demo.common.service.jvm.oom.HeapOOM
 */
    
//      Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

### 2.虚拟机栈和本地方法栈溢出

```java
/**
 * vm args: -Xss128k -XX:+PrintGCDetails
 * @see com.demo.common.service.jvm.oom.StackOOM
 */

//    Exception in thread "main" java.lang.StackOverflowError
```

### 3.方法去和运行时常量池溢出

```java
/**
 * 1.6 vm args: -XX:PermSIze=16m -XX:MaxPermSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
 * 1.7 vm args: -XX:MetaspaceSize=16m -XX:MaxMetaspaceSize=16m -Xms256m -Xmx256m -XX:+PrintGCDetails
 * @see com.demo.common.service.jvm.oom.RuntimeConstantOOM
 * @see com.demo.common.service.jvm.oom.MethodAreaOOM
 */

//		Exception in thread "main" java.lang.OutOfMemoryError: Metaspace  
```
*String.intern（）是一个Native方法*

*在JDK 1.6中，intern（）方法会把首次遇到的字符串实例复制 到永久代中，返回的也是永久代中这个字符串实例的引用*

*在JDK 1.7中，intern（）方法不会再复制实例，只是在常量池中记录首次出现的实例引用(地址)*

### 4.本机直接内存溢出

```java
/**
 * vm args: -Xms20m -Xmx20m -XX:MaxDirectMemorySize=10M -XX:+PrintGCDetails
 * @see com.demo.common.service.jvm.oom.DirectMemoryOOM
 */

// java.lang.OutOfMemoryError: Direct buffer memory
```

