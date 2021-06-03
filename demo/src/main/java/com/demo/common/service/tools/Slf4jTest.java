package com.demo.common.service.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Slf4jTest {
    private static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) throws Exception {
        //        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        //        JoranConfigurator configurator = new JoranConfigurator();
        //        configurator.setContext(lc);
        //        lc.reset();
        //        try {
        //            configurator.doConfigure("logback.xml");
        //        } catch (JoranException e) {
        //            e.printStackTrace();
        //        }
        //        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        //        System.out.println("===================");


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

    public static void ke() {
        abc();
    }

    public static void abc() {
        try {
            new InputStreamReader(new FileInputStream("/c/k.jpg"));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }


}
