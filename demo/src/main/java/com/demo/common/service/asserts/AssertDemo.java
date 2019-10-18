package com.demo.common.service.asserts;

import org.junit.Test;
import org.springframework.util.Assert;

public class AssertDemo {
    @Test
    public void test(){
        Assert.isTrue(1 + 1 < 2, "对！");

    }
}
