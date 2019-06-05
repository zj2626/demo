package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator;

import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.Condiment;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.Pancake;

public class Ham extends Condiment {
    public Ham(Pancake pancake) {
        super(pancake);
    }

    @Override
    public void price() {
        System.out.print("加火腿 ");
        super.price();
    }
}
