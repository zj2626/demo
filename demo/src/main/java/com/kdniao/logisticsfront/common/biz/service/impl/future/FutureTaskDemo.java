package com.kdniao.logisticsfront.common.biz.service.impl.future;

import java.util.concurrent.*;

public class FutureTaskDemo<String> extends FutureTask<String> {
    public FutureTaskDemo(Callable callable) {
        super(callable);
    }

    public FutureTaskDemo(Runnable runnable, String result) {
        super(runnable, result);
    }
}