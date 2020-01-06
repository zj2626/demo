package com.demo.common.service.spring.learn2.aop.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class AopConfig {

    @Pointcut("execution(public * com.demo.common.service.spring.learn2..*.query*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("@Before: 前置通知");
    }

    @Around("pointCut()")
    public Object doAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around:进入方法---环绕通知");
        Object o = joinPoint.proceed();
        System.out.println("@Around:退出方法---环绕通知");
        return o;
    }
}
