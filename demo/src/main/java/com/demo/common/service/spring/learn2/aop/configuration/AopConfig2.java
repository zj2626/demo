package com.demo.common.service.spring.learn2.aop.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.After;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Aspect

// 为所有代理对象是DemoService的单独创建一个切面(满足条件:切面和代理对象的生命周期都是prototype)
//@Aspect("perthis(this(com.demo.common.service.spring.learn2.aop.bean.DemoService))")
//@Scope("prototype")
@Component
public class AopConfig2 {
    /**
     * Before——在方法调用之前调用通知
     * After——在方法完成之后调用通知，无论方法执行成功与否
     * After-returning——在方法执行成功之后调用通知
     * After-throwing——在方法抛出异常后进行通知
     * Around——通知包裹了被通知的方法，在被通知的方法调用之前和调用之后执行自定义的行为
     * *
     * *
     * 连接点：
     * 定义：连接点是一个应用执行过程中能够插入一个切面的点。
     * 连接点可以是调用方法时、抛出异常时、甚至修改字段时、
     * 切面代码可以利用这些点插入到应用的正规流程中
     * 程序执行过程中能够应用通知的所有点。
     * *
     * 切点：
     * 定义：如果通知定义了“什么”和“何时”。那么切点就定义了“何处”。切点会匹配通知所要织入的一个或者多个连接点。
     * 通常使用明确的类或者方法来指定这些切点。
     * 作用：定义通知被应用的位置（在哪些连接点）
     * *
     * 切面：
     * 定义：切面是通知和切点的集合，通知和切点共同定义了切面的全部功能——它是什么，在何时何处完成其功能。
     * *
     * 引入：
     * 引入允许我们向现有的类中添加方法或属性
     * *
     * 织入：
     * 织入是将切面应用到目标对象来创建的代理对象过程。
     * 切面在指定的连接点被织入到目标对象中，在目标对象的生命周期中有多个点可以织入
     */

    @Pointcut("execution(public * com.demo.common.service.spring.learn2..*.query*(..))")
    public void pointCutExecution() {}

    /**
     * 目标方法满足pointCutExecution()表达式且要满足方法参数中第一个参数类型为String
     *
     * @param joinPoint
     * @param requestParam
     */
    @Before("pointCutExecution() && args(requestParam, ..)")
    public void before(JoinPoint joinPoint, String requestParam) {
        System.out.println("@Before: 前置通知-" + " >>> " + this.hashCode());
    }

    @Before(value = "target(id) && target(name)", argNames="id,name")
    public void after(String id, String name) {
        System.out.println("@Before: 前置通知2-" + " >>> " + this.hashCode());
    }

    @Around("pointCutExecution()")
    public Object doAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("\n@Around:进入方法---环绕通知-");
        Object o = joinPoint.proceed();
        System.out.println("@Around:退出方法---环绕通知-");
        return o;
    }
}
