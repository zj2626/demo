package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.interpreter.design.map;

// 其他的类 　代表逻辑“或”操作的Or类OrExpression
public class OrExpression implements Expression {
    private Expression expressionA;
    private Expression expressionB;

    public OrExpression(Expression expressionA, Expression expressionB) {
        this.expressionA = expressionA;
        this.expressionB = expressionB;
    }

    @Override
    public boolean interpret(String context) {
        return expressionA.interpret(context) || expressionB.interpret(context);
    }
}
