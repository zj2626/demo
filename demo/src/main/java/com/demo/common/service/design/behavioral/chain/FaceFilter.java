package com.demo.common.service.design.behavioral.chain;

public class FaceFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("FaceFilter");
        request.setRequestStr(request.getRequestStr() + " -> " + "FaceFilter");

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + " -> " + "FaceFilter");
    }
}
