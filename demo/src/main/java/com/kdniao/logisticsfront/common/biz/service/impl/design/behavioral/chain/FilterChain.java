package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作责任链模式的类
 * <p>
 * 功能: 根据客户端配置加入的拦截器串进行拦截(模拟过滤规则形成的拦截链条)
 */
public class FilterChain implements Filter {
    List<Filter> filters = new ArrayList<>();
    private int index = 0;

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);

        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        if (index < filters.size()) {
            // 按照filters中顺序一个一个执行filter实例
            Filter filter = this.filters.get(index);

            index++;

            filter.doFilter(request, response, chain);
        }
    }
}
