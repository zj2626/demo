package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain;

public class HTMLFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("HTMLFilter");
        request.setRequestStr(request.getRequestStr() + " -> " + "HTMLFilter");

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + " -> " + "HTMLFilter");
    }
}
