package com.demo.common.service.spring.learn2.aop.configuration;

import com.demo.common.service.spring.learn2.aop.bean.MyLogs;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

// 开启Aspect支持, 也可以使用xml形式: <aop:aspectj-autoproxy/>
@EnableAspectJAutoProxy/*(proxyTargetClass = true)*/
// 声明一个切面
@Aspect
// 切面交由spring管理
@Component
public class AopConfig {

    /**
     * execution: 定义到方法参数/修饰符...级别
     * 表达式含义:
     * 1.方法可见性: public  (可选)
     * 2.返回值类型: *       (表示任意)
     * 3.方法全路径名:
     * 4.方法名:   query*
     * 5.方法参数类型:  String, ..
     * 6.方法抛出的异常类型:  (可选)
     */
    @Pointcut("execution(public * com.demo.common.service.spring.learn2..*.query*(String, .., Integer, ..))")
    public void pointCutExecution() {}

    @Before("pointCutExecution()")
    public void before0(JoinPoint joinPoint) {
        System.out.println("@Before: pointCutExecution");
    }

    /**
     * within: 定义到类级别
     */
    @Pointcut("within(com.demo.common.service.spring.learn2..*.*)")
    public void pointCutWithin() {}

    @Before("pointCutWithin()")
    public void before1(JoinPoint joinPoint) {
        System.out.println("@Before: pointCutWithin");
    }

    /**
     * args: 定义到类级别
     */
    @Pointcut("args(String, .., Integer)")
    public void pointCutArgs() {}

    @Before("pointCutArgs()")
    public void before2(JoinPoint joinPoint) {
        System.out.println("@Before: pointCutArgs");
    }

    /**
     * @annotation: 定义到注解级别
     */
    @Pointcut("@annotation(com.demo.common.service.spring.learn2.aop.bean.MyLogs)")
    public void pointCutAnnotation() {}

    @Before("pointCutAnnotation()")
    public void before3(JoinPoint joinPoint) {
        System.out.println("@Before: @annotation");
    }

    // 同上, 可省略pointCut方法
    @Before("@annotation(myLogs)")
    public void before4(JoinPoint joinPoint, MyLogs myLogs) {
        System.out.println("@Before: @annotation " + myLogs.value());
    }

    /**
     * this: 定义到指定代理对象 (JDK动态代理和cglib动态代理都可以匹配到对应的代理对象)
     */
    @Before("this(com.demo.common.service.spring.learn2.aop.bean.DemoService)")
    public void before5(JoinPoint joinPoint) {
        System.out.println("@Before: this DemoService");
    }

    /**
     * this: 定义到指定代理对象 (只有cglib动态代理都可以匹配到对应的代理对象,可设置(proxyTargetClass = true)实现)
     */
    @Before("this(com.demo.common.service.spring.learn2.aop.bean.DemoServiceImpl)")
    public void before6(JoinPoint joinPoint) {
        System.out.println("@Before: this DemoServiceImpl");
    }

    /**
     * target: 定义到指定目标对象(被代理对象DemoServiceImpl,JDK动态代理和cglib动态代理都可以匹配)
     */
    @Before("target(com.demo.common.service.spring.learn2.aop.bean.DemoService)")
    public void before7(JoinPoint joinPoint) {
        System.out.println("@Before: target DemoService");
    }

    /**
     * target: 定义到指定目标对象(被代理对象DemoServiceImpl,JDK动态代理和cglib动态代理都可以匹配)
     */
    @Before("target(com.demo.common.service.spring.learn2.aop.bean.DemoServiceImpl)")
    public void before8(JoinPoint joinPoint) {
        System.out.println("@Before: target DemoServiceImpl");
    }
}
