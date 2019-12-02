package com.demo.common.service.thread.system.chain;

import org.junit.Test;

/**
 * 责任链模式
 */
public class Main {

    @Test
    public void test() throws InterruptedException {
        Request request = new Request();
        request.setName("FFFFFFFFFFFF");

        PrintProcessor pa = new PrintProcessor();
        pa.start();

        SaveProcessor pb = new SaveProcessor();
        pb.setNextProcessor(pa);
        pb.start();

        pb.processorRequest(request);

        Thread.sleep(500);
    }
}
