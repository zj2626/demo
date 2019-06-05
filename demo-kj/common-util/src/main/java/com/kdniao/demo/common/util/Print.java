package com.kdniao.demo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Print {
    private static final Logger logger = LoggerFactory.getLogger(Print.class);

    public static void out(String msg, Object info) {
        if (info != null) {
            logger.info("【" + msg + "】：" + info);
        } else {
            logger.info("【" + msg + "】");
        }
    }
}
