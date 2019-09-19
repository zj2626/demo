package com.demo.common.service.design.behavioral.chain;

public class SensitiveFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("SensitiveFilter");
        request.setRequestStr(request.getRequestStr() + " -> " + "SensitiveFilter");

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + " -> " + "SensitiveFilter");
    }
}
