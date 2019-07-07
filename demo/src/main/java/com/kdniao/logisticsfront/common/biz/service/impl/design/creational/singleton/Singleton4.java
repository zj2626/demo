package com.kdniao.logisticsfront.common.biz.service.impl.design.creational.singleton;

public enum Singleton4 {
    RED() {
        private int r = 255;
        private int g = 0;
        private int b = 0;

    },
    BLACK() {
        private int r = 0;
        private int g = 0;
        private int b = 0;
    },
    WHITE() {
        private int r = 255;
        private int g = 255;
        private int b = 255;
    };
}
