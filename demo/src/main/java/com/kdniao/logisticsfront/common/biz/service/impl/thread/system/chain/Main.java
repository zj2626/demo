package com.kdniao.logisticsfront.common.biz.service.impl.thread.system.chain;

import org.junit.Test;

public class Main {

    @Test
    public void test() throws InterruptedException {
        PrintProcessor pa = new PrintProcessor();
        pa.start();
        PrintProcessor pb = new PrintProcessor();
        pb.setNextProcessor(pa);
        pb.start();
        SaveProcessor pc = new SaveProcessor();
        pc.setNextProcessor(pb);
        pc.start();
        SaveProcessor pd = new SaveProcessor();
        pd.setNextProcessor(pc);

        Request request = new Request();
        request.setName("FFFFFFFFFFFF");
        pd.processorRequest(request);

        pd.start();

        Thread.sleep(500);
    }
}
