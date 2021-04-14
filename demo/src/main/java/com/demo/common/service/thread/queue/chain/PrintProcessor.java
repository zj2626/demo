package com.demo.common.service.thread.queue.chain;

import java.util.concurrent.LinkedBlockingQueue;

public class PrintProcessor extends Thread implements RequestProcessor {
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
                System.out.println(Thread.currentThread().getName() + " PrintProcessor : " + request);

                if(nextProcessor != null) {
                    nextProcessor.processorRequest(request);
                }else{
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
