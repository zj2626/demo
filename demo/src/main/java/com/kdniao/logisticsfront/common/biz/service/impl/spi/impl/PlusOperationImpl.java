package com.kdniao.logisticsfront.common.biz.service.impl.spi.impl;

import com.kdniao.logisticsfront.common.biz.service.impl.spi.api.IOperation;

public class PlusOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA + numberB;
    }
}
