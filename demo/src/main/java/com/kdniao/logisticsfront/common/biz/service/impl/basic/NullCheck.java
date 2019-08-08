package com.kdniao.logisticsfront.common.biz.service.impl.basic;

import org.junit.Test;

import java.util.Optional;

public class NullCheck {
    @Test
    public void test() {
        String result = null;

        Father father = new Father();
        Son son = new Son();
        son.setFather(father);
        System.out.println(son.getFather());
        System.out.println(son.getFather().getGrand());
        System.out.println("-----------------");

        Optional<String> name = Optional.of(son).map(Son::getFather).map(Father::getGrand).map(Grand::getName);
        if (name.isPresent()) {
            result = name.get();
        }
        System.out.println(result);
    }

    private class Son {
        private Father father;

        public Father getFather() {
            return father;
        }

        public void setFather(Father father) {
            this.father = father;
        }
    }

    private class Father {
        private Grand grand;

        public Grand getGrand() {
            return grand;
        }

        public void setGrand(Grand grand) {
            this.grand = grand;
        }
    }

    private class Grand {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
