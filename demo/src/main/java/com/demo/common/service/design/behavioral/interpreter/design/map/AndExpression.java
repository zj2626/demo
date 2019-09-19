package com.demo.common.service.design.behavioral.interpreter.design.map;

// 其他的类 　代表逻辑“与”操作的And类
public class AndExpression implements Expression {
    private Expression expressionA;
    private Expression expressionB;

    public AndExpression(Expression expressionA, Expression expressionB) {
        this.expressionA = expressionA;
        this.expressionB = expressionB;
    }

    @Override
    public boolean interpret(String context) {
        return expressionA.interpret(context) && expressionB.interpret(context);
    }
}
