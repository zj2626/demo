package com.demo.common.service.design.structural.proxy.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * > Proxy 的静态方法
 * <p>
 * // 方法 1: 该方法用于获取指定代理对象所关联的调用处理器
 * static InvocationHandler getInvocationHandler(Object proxy)
 * <p>
 * // 方法 2：该方法用于获取关联于指定类装载器和一组接口的动态代理类的类对象
 * static Class getProxyClass(ClassLoader loader, Class[] interfaces)
 * <p>
 * // 方法 3：该方法用于判断指定类对象是否是一个动态代理类
 * static boolean isProxyClass(Class cl)
 * <p>
 * // 方法 4：该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
 * static Object newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)
 * <p>
 * <p>
 * > InvocationHandler 的核心方法
 * <p>
 * // 该方法负责集中处理动态代理类上的所有方法调用。第一个参数既是代理类实例，第二个参数是被调用的方法对象
 * // 第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
 * Object invoke(Object proxy, Method method, Object[] args)
 * <p>
 * <p>
 * Java 动态代理 > 步骤 (同下)
 * 通过实现 InvocationHandler 接口创建自己的调用处理器；
 * 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
 * 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型(invocationHandler)；
 * 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
 * <p>
 * <p>
 * Java 动态代理 > 步骤 (Proxy的静态方法newProxyInstance已经为我们封装了步骤2到步骤4的过程)
 * // InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
 * // 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
 * InvocationHandler handler = new InvocationHandlerImpl(..);
 * // 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
 * Class clazz = Proxy.getProxyClass(classLoader, new Class[] { Interface.class, ... });
 * // 通过反射从生成的类对象获得构造函数对象
 * Constructor constructor = clazz.getConstructor(new Class[] { InvocationHandler.class });
 * // 通过构造函数对象创建动态代理类实例
 * Interface Proxy = (Interface)constructor.newInstance(new Object[] { handler });
 */
public class ProxyFactory implements InvocationHandler {
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    // way 2
    public Object getProxyInstance() {
        // 创建动态代理类实例对象 获取被代理的对象信息
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Exception {
        beforeRequest();

        Object result = method.invoke(target, parameters);

        afterRequest();

        return result;
    }

    private void beforeRequest() {
        System.out.println("-------------------- beforeRequest");
    }

    private void afterRequest() {
        System.out.println("-------------------- afterRequest\n\n");
    }

}
