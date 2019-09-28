package com.demo.common.service.optional;

import org.junit.Test;

import java.util.Optional;

public class NullCheck {
    @Test
    public void test() {
        String result = null;

        Father father = new Father("zhangsan");

        Son son = new Son("s-zhang");
        son.setFather(father);

        System.out.println("-----------------");
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
        private String name;
        private Father father;

        public Son(String name) {
            this.name = name;
        }

        public Father getFather() {
            return father;
        }

        public void setFather(Father father) {
            this.father = father;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append(",\"father\":")
                    .append(father);
            sb.append('}');
            return sb.toString();
        }
    }

    private class Father {
        private String name;
        private Grand grand;

        public Father(String name) {
            this.name = name;
        }

        public Grand getGrand() {
            return grand;
        }

        public void setGrand(Grand grand) {
            this.grand = grand;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append(",\"grand\":")
                    .append(grand);
            sb.append('}');
            return sb.toString();
        }
    }

    private class Grand {
        private String name;

        public Grand(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }
}
