package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain;

public interface Filter {
    void doFilter(Request request,Response response,FilterChain chain);
}
