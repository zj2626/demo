package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain;

import org.junit.Test;

/**
 * 6. 责任链模式(Chain of Responsibility Pattern)
 * <p>
 * 责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。
 * <p>
 * 在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。
 *
 * 特点:在客户端设置责任链中每个的处理部分有谁以及其顺序
 * @author zhangj
 * @version $Id: StructDemoTest.java, v 0.1 2019/5/27 16:34 zhangj Exp $
 */

public class StructDemoTest {

    @Test
    public void test1() {
        Request request = new Request();
        request.setRequestStr("start");
        Response response = new Response();
        response.setResponseStr("start");

        FilterChain chain = new FilterChain();
        chain.addFilter(new HTMLFilter()).addFilter(new FaceFilter()).addFilter(new SensitiveFilter());

        chain.doFilter(request, response, chain);

        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());
    }

}
/**
 * 优点：
 * 1、降低耦合度。它将请求的发送者和接收者解耦。
 * 2、简化了对象。使得对象不需要知道链的结构。
 * 3、增强给对象指派职责的灵活性。通过改变链内的成员或者调动它们的次序，允许动态地新增或者删除责任。
 * 4、增加新的请求处理类很方便。
 * <p>
 * 缺点：
 * 1、不能保证请求一定被接收。
 * 2、系统性能将受到一定影响，而且在进行代码调试时不太方便，可能会造成循环调用。
 * 3、可能不容易观察运行时的特征，有碍于除错。
 */
