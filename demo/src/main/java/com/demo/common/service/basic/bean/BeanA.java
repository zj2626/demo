package com.demo.common.service.basic.bean;

public class BeanA {
    private String name;
    private BeanB b;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BeanB getB() {
        return b;
    }

    public void setB(BeanB b) {
        this.b = b;
    }

    public static class BeanB {
        private String age;
        private static final BeanA single = new BeanA();

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public static BeanA getSingle() {
            return single;
        }
    }
}
