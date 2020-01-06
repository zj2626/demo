package hello.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(3)
@Component
public class MyAopConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAopConfig.class);


    @Pointcut("execution(* hello.service..DoHSomethingImpl.*(..))")
    public void checkData() {
    }

    @Pointcut("execution(* hello.service..DoHSomethingImpl.*(..))")
    private void writeLog() {
    }

    @Before("checkData()")
    public void doBeforeMethod(JoinPoint joinPoint) {
        System.out.println("--@Before:前置通知");
        System.out.println("--@Before: > " + joinPoint.toString());
    }

    @AfterReturning(value = "writeLog()", returning = "result")
    public void doAfterMethod(String result) {
        System.out.println("-----@AfterReturning:后置通知");
        System.out.println("@AfterReturning > " + result);
    }

    //声明例外通知
    @AfterThrowing(pointcut = "checkData() || writeLog()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        System.out.println("---@AfterThrowing:例外通知");
        System.out.println("---@AfterThrowing > " + e.getMessage());
    }

    //声明最终通知
    @After("checkData() || writeLog()")
    public void doAfter() {
        System.out.println("----@After:最终通知");
    }

    @Around("checkData() || writeLog()")
    public Object doAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("-@Around:进入方法---环绕通知");
        Object o = joinPoint.proceed();
        System.out.println("---@Around:退出方法---环绕通知");

        return o;
    }
}
