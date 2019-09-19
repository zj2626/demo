package com.demo.common.service.log;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;

public class LogTest {
    private static Logger logger = Logger.getLogger(LogTest.class);

    public static void main(String[] args) throws Exception {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");

        fun();
    }

    public static void fun() {
        // 记录debug级别的信息
        logger.debug("This is debug message. from fun");
        // 记录info级别的信息
        logger.info("This is info message. from fun");
        // 记录error级别的信息
        logger.error("This is error message. from fun");

        ke();
    }

    public static void ke(){
        abc();
    }

    public static void abc(){
        try {
            new InputStreamReader(new FileInputStream("/c/k.jpg"));
        } catch (FileNotFoundException e) {
            logger.error(new Date());
            logger.error(e.getMessage(), e);
        }
    }


}
