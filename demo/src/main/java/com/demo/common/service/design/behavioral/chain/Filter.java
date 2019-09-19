package com.demo.common.service.design.behavioral.chain;

public interface Filter {
    void doFilter(Request request,Response response,FilterChain chain);
}
