package com.kdniao.logisticsfront.common.biz.service.impl.spi.spi;

import com.kdniao.logisticsfront.common.biz.service.impl.spi.api.IOperation;

public class DivisionOperationImpl implements IOperation {
    @Override
    public int operation(int numberA, int numberB) {
        return numberA / numberB;
    }
}
