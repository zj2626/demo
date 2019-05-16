package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator;

import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.Condiment;
import com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.Pancake;

public class FiredEgg extends Condiment {
    public FiredEgg(Pancake pancake) {
        super(pancake);
    }

    @Override
    public void price() {
        System.out.print("加鸡蛋 ");
        super.price();
    }
}
