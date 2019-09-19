package com.demo.common.service.design.structural.composite.design.map;

import org.junit.Test;

/**
 *  (1) 透明组合模式
 *       透明组合模式中，抽象构件Component中声明了所有用于管理成员对象的方法，包括add()、remove()以及getChild()等方法，
 *       这样做的好处是确保所有的构件类都有相同的接口。在客户端看来，叶子对象与容器对象所提供的方法是一致的，客户端可以相同地对待所有的对象。
 *       透明组合模式也是组合模式的标准形式，虽然上面的解决方案一在客户端可以有不透明的实现方法，
 *       但是由于在抽象构件中包含add()、remove()等方法，因此它还是透明组合模式
 *
 *       透明组合模式的缺点是不够安全，因为叶子对象和容器对象在本质上是有区别的。
 *       叶子对象不可能有下一个层次的对象，即不可能包含成员对象，因此为其提供add()、remove()以及getChild()等方法是没有意义的，
 *       这在编译阶段不会出错，但在运行阶段如果调用这些方法可能会出错（如果没有提供相应的错误处理代码）
 *
 *  (2) 安全组合模式
 *       安全组合模式中，在抽象构件Component中没有声明任何用于管理成员对象的方法，而是在Composite类中声明并实现这些方法。
 *       这种做法是安全的，因为根本不向叶子对象提供这些管理成员对象的方法，
 *       对于叶子对象，客户端不可能调用到这些方法，这就是解决方案二所采用的实现方式
 *
 *       安全组合模式的缺点是不够透明，因为叶子构件和容器构件具有不同的方法，且容器构件中那些用于管理成员对象的方法没有在抽象构件类中定义，
 *       因此客户端不能完全针对抽象编程，必须有区别地对待叶子构件和容器构件。
 *       在实际应用中，安全组合模式的使用频率也非常高，在Java AWT中使用的组合模式就是安全组合模式。
 */
public class Client {
    @Test
    public void test1() {
        Composite root = new Composite();
        root.setName("根目录");

        Leaf file = new Leaf();
        file.setName("第一个文件");
        root.add(file);

        Composite folder = new Composite();
        folder.setName("第一个文件夹");
        root.add(folder);

        Leaf file2 = new Leaf();
        file2.setName("第er个文件");
        folder.add(file2);

        root.display();
    }
}
