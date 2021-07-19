package com.demo.common.service.design.structural.proxy.demo3;

import org.junit.Test;

/**
 * Cglib代理 可以是普通类 不需要实现接口
 * <p>
 * 由于JDK只能针对实现了接口的类做动态代理，而不能对没有实现接口的类做动态代理，所以cgLib横空出世！
 * CGLib（Code Generation Library）是一个强大、高性能的Code生成类库，
 * 它可以在程序运行期间动态扩展类或接口，它的底层是使用java字节码操作框架ASM实现。
 */
public class StructDemoTest {

    @Test
    public void test1() {
        // 代理的类不能为final
        RealSubject realSubject = new RealSubject();

        RealSubject proxySubject = (RealSubject) new ProxyFactory(realSubject).getProxyInstance();

        System.out.println(proxySubject.getClass() + "\n");

        proxySubject.request("sex !");
        proxySubject.request2("sex2 !");

        // 无效
        proxySubject.fuck("sssss");
    }

}
