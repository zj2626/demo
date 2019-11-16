package com.demo.common.service.asserts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

public class SpringAssertDemo {
    @Test
    public void test() {
        String msg = " ";
        try {
            Assert.isTrue(1 + 1 == 2, "!isTrue");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 不为空
            Assert.notNull(msg, "!notNull");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 不为空且不为空字符串
            Assert.hasLength(msg, "!hasLength");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 不为空不为空字符串且至少包含一个非空格的字符
            Assert.hasText(msg, "!hasText");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 等同于 !CollectionUtils.isEmpty()
            Assert.notEmpty(Collections.singletonList(msg), "!notEmpty");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void before() {
        System.out.println("START");
    }

    @After
    public void after() {
        System.out.println("END");
    }
}
