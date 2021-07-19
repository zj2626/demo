package com.demo.common.service.design.structural.proxy.demo1;

public class ProxyFactory implements Subject {
    private Subject target;

    public ProxyFactory(Subject target) {
        this.target = target;
    }

    @Override
    public String request(String parameter) {
        beforeRequest();

        target.request(parameter);

        afterRequest();

        return "success";
    }

    private void beforeRequest() {
        System.out.println("-------------------- beforeRequest");
    }

    private void afterRequest() {
        System.out.println("-------------------- afterRequest\n\n");
    }

}
