package com.demo.common.service.thread.callable.chain;

import java.util.concurrent.LinkedBlockingQueue;

public class SaveProcessor extends Thread implements RequestProcessor {
    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    private RequestProcessor nextProcessor;

    public void setNextProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void processorRequest(Request request) {
        queue.add(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = queue.take();
                System.out.println(Thread.currentThread().getName() + " SaveProcessor : " + request);

                nextProcessor.processorRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
