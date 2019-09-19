package com.demo.common.service.design.behavioral.interpreter.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        Expression numberA = new TerminalExpression("测试A");
        Expression numberB = new TerminalExpression("测试B");

        Expression andExpression = new AndExpression(numberA, numberB);
        Expression orExpression = new OrExpression(numberA, numberB);

        System.out.println(andExpression.interpret("测试ABCDEF"));
        System.out.println(orExpression.interpret("测试ABCDEF"));
    }
}
