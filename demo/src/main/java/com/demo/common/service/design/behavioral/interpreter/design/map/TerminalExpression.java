package com.demo.common.service.design.behavioral.interpreter.design.map;

// 主要解释器 TerminalExpression 类
public class TerminalExpression implements Expression {
    private String data;

    public TerminalExpression(String data) {
        this.data = data;
    }

    @Override
    public boolean interpret(String context) {
        return context.contains(data);
    }
}
