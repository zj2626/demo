package com.demo.common.service.design.behavioral.iterator;

import org.junit.Test;

/**
 * 7. 迭代器模式（Iterator）
 * <p>
 * 提供一种方法顺序访问一个聚合对象中的各种元素，而又不暴露该对象的内部表示
 * <p>
 * 举例:
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/22 18:30 zhangj Exp $
 */

public class StructDemoTest {

    @Test
    public void test1() {
    }

}
/**
 * 迭代器模式的优点有：
 * 简化了遍历方式，对于对象集合的遍历，还是比较麻烦的，对于数组或者有序列表，我们尚可以通过游标来取得，但用户需要在对集合了解很清楚的前提下，自行遍历对象，但是对于hash表来说，用户遍历起来就比较麻烦了。而引入了迭代器方法后，用户用起来就简单的多了。
 * 可以提供多种遍历方式，比如说对有序列表，我们可以根据需要提供正序遍历，倒序遍历两种迭代器，用户用起来只需要得到我们实现好的迭代器，就可以方便的对集合进行遍历了。
 * 封装性良好，用户只需要得到迭代器就可以遍历，而对于遍历算法则不用去关心。
 * <p>
 * 迭代器模式的缺点：
 * 对于比较简单的遍历（像数组或者有序列表），使用迭代器方式遍历较为繁琐，大家可能都有感觉，像ArrayList，我们宁可愿意使用for循环和get方法来遍历集合。
 */
