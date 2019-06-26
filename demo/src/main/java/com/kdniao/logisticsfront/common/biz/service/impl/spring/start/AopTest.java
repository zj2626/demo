package com.kdniao.logisticsfront.common.biz.service.impl.spring.start;

import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.BeanInterface;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.MyBean;
import org.aopalliance.aop.Advice;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.*;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * AOP(面向切面): 就是用来批量定义程序逻辑
 * 1.aop定义组件
 * 2.通知,拦截组件
 * 3.调用执行组件[java proxy / cglib proxy]
 * 4.
 */

// 代理对象的创建 设置切面 添加/删除通知 (前置/后置/异常...) 动态添加通知
public class AopTest {
    private BeanInterface target;

    @Before
    public void setUp() throws Exception {
        target = new MyBean();
        target.setId(12);
        target.setName("Name12");
    }


    // 使用普通的proxyFactory
    @Test
    public void creatProxyTest() {
        // spring代理工厂
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new MethodBeforeAdviceInterceptor((method, args, target1) -> System.out.println(">>>>>>>>>>>>>>>>>>>>>>")));

        BeanInterface myBean = (BeanInterface) proxyFactory.getProxy();
        System.out.println(myBean);
        System.out.println(myBean.getId());
        System.out.println(myBean.getName());
    }

    // 使用AspectJ proxyFactory
    @Test
    public void creatProxyAspectJTest() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(new MethodBeforeAdviceInterceptor((method, args, target1) -> System.out.println(">>>>>>>>>>>>>>>>>>>>>>")));
        advisor.setExpression("execution(* *.*Name(..))");

        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(target);
        proxyFactory.addAdvisor(advisor);

        BeanInterface myBean = proxyFactory.getProxy();
        // *Name的方法会执行前置切面方法
        System.out.println("######1 n");
        System.out.println(myBean);
        System.out.println("######2 n");
        System.out.println(myBean.getId());
        System.out.println("######3 n");
        myBean.setId(123);
        System.out.println("######4 y");
        System.out.println(myBean.getName());
        System.out.println("######5 y");
        myBean.setName("BBB");
    }

    // 动态移除添加的通知 一个通知可以添加删除多次 并且会执行多次
    @Test
    public void creatProxyAspectJTest2() {
        Advice afterReturningAdviceInterceptor = new AfterReturningAdviceInterceptor((returnValue, method, args, target1) -> System.out.println("_______________________A2"));
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(afterReturningAdviceInterceptor);
        advisor.setExpression("execution(* *.*Name(..))");

        Advice methodBeforeAdvice = (MethodBeforeAdvice) (method, args, target12) -> System.out.println("*****************A3");

        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(target);
        BeanInterface myBean = proxyFactory.getProxy();

        System.out.println("当前");
        print(myBean);

        System.out.println("添加通知 advisor");
        proxyFactory.addAdvisor(advisor);
        print(myBean);

        System.out.println("去除通知 advisor");
        proxyFactory.removeAdvisor(advisor);
        print(myBean);

        Advised advised = (Advised) myBean;

        System.out.println("添加通知 advice"); // 不通过advisor添加的拦截器会在每个方法上加入
        advised.addAdvice(methodBeforeAdvice);
        print(myBean);

        System.out.println("去除通知 advice");
        advised.removeAdvice(methodBeforeAdvice);
        print(myBean);
    }

    @Test
    public void creatProxyAspectJTest3() {
        Advice afterReturningAdviceInterceptor = new AfterReturningAdviceInterceptor((returnValue, method, args, target1) -> System.out.println("_______________________A2"));
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(afterReturningAdviceInterceptor);

        Advice advice = (MethodBeforeAdvice) (method, args, target12) -> System.out.println("*****************A3");

        AdvisedSupport config = new AdvisedSupport();
        config.setTarget(target);
        config.addAdvisor(advisor);
        config.addAdvice(advice);

        AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();
        BeanInterface myBean = (BeanInterface) aopProxyFactory.createAopProxy(config).getProxy();
        print(myBean);

    }

    private void print(BeanInterface myBean) {
        System.out.println("########");
        myBean.setId(1);
        myBean.setName("BBB");
        myBean.setName("BBB");
        System.out.println("########\n");
    }
}
