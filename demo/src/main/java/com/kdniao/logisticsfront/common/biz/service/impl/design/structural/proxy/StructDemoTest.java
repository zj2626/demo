package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy;

import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2.ProxyFactory;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2.ProxyFactory2;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2.RealSubject;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.proxy.demo2.Subject;
import org.junit.Test;

/**
 * 6. 代理模式(Proxy Pattern)
 * <p>
 * 代理模式(Proxy Pattern) ：给某一个对象提供一个代理，并由代理对象控制对原对象的引用。
 * 代理模式的英文叫做Proxy或Surrogate，它是一种对象结构型模式。
 * <p>
 * 举例: Spring AOP
 *
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/14 17:00 zhangj Exp $
 */
public class StructDemoTest {

    @Test
    public void test1() {
        Subject proxySubject = (Subject) new ProxyFactory2(new RealSubject()).getProxyInstance();

        proxySubject.request("sex !");
        proxySubject.request2("sex2 !", "sex3 -|");
    }

}
/**
 * 6.8. 优点
 * 代理模式的优点
 * <p>
 * 代理模式能够协调调用者和被调用者，在一定程度上降低了系 统的耦合度。
 * 远程代理使得客户端可以访问在远程机器上的对象，远程机器 可能具有更好的计算性能与处理速度，可以快速响应并处理客户端请求。
 * 虚拟代理通过使用一个小对象来代表一个大对象，可以减少系 统资源的消耗，对系统进行优化并提高运行速度。
 * 保护代理可以控制对真实对象的使用权限。
 * 6.9. 缺点
 * 代理模式的缺点
 * <p>
 * 由于在客户端和真实主题之间增加了代理对象，因此 有些类型的代理模式可能会造成请求的处理速度变慢。
 * 实现代理模式需要额外的工作，有些代理模式的实现 非常复杂。
 */
