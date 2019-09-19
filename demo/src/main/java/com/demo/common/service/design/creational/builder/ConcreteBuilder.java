package com.demo.common.service.design.creational.builder;

/**
 * ConcreteBuilder：具体建造者
 */
public class ConcreteBuilder implements Builder {
    private Product product;

    public ConcreteBuilder() {
        product = new Product();
    }

    @Override
    public void buildBasic() {
        product.setBasic("打好基础");
    }

    @Override
    public void buildWalls() {
        product.setWall("砌墙");
    }

    @Override
    public void roofed() {
        product.setRoofed("封顶大吉");
    }

    @Override
    public Product buildProduct() {
        return product;
    }

}
