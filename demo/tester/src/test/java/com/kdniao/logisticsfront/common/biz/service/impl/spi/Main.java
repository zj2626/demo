package com.kdniao.logisticsfront.common.biz.service.impl.spi;

import com.kdniao.logisticsfront.common.biz.service.impl.spi.api.IOperation;
import com.kdniao.logisticsfront.common.biz.service.impl.spi.impl.PlusOperationImpl;
import com.kdniao.logisticsfront.common.biz.service.impl.spi.spi.DivisionOperationImpl;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        IOperation plus = new PlusOperationImpl();
        System.out.println(plus.operation(5, 3));
        IOperation division = new DivisionOperationImpl();
        System.out.println(division.operation(9, 3));

        System.out.println("classPath:" + System.getProperty("java.class.path"));
        ServiceLoader<IOperation> serviceLoader = ServiceLoader.load(IOperation.class);
        Iterator<IOperation> operationIterator = serviceLoader.iterator();
        while (operationIterator.hasNext()) {
            IOperation operation = operationIterator.next();
            System.out.println(operation.operation(6, 3));
        }
    }
}
