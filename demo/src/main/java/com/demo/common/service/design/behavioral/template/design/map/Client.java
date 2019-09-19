package com.demo.common.service.design.behavioral.template.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        Game game = new Cricket();
        game.play();
        Game game2 = new Football();
        game2.play();
    }
}
