package com.demo.common.service.spi.impl;

import com.demo.common.service.spi.api.IOperation;

public class PlusOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA + numberB;
    }
}
