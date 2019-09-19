package com.demo.common.service.spi.spi;

import com.demo.common.service.spi.api.IOperation;

public class DivisionOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA / numberB;
    }
}
