package com.demo.common.service.thread.abs;

import org.junit.After;
import org.junit.Before;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public abstract class MyExcutor {
    private LocalDateTime begin;
    private LocalDateTime end;
    protected ExcutorPoolDemo excutorPool;
    protected ForkJoinPoolDemo forkJoinPool;

    public abstract Object doExcute(Map<String, Object> parameter) throws Exception;

    public Object doExcuteRead(Map<String, Object> ignore) throws Exception {
        return "error";
    }

    /**
     * 自己计算的时长
     */
    public void calculate(Long n){
        if(null != n){
            Duration duration = Duration.ofMillis(n);
            System.out.println("theory: " + duration.toMillis());
        }
    }

    @Before
    public void before(){
        begin = LocalDateTime.now();
    }

    @After
    public void after(){
        end = LocalDateTime.now();
        Duration duration = Duration.between(begin, end);
        System.out.println("consequence: " + duration.toMillis());
    }
}
