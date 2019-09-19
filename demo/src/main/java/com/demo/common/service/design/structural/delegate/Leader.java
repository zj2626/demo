package com.demo.common.service.design.structural.delegate;

import java.util.HashMap;
import java.util.Map;

// Delegate
public class Leader implements IExcuter {
    private Map<String, IExcuter> excuters = new HashMap<>();

    public Leader() {
        excuters.put("a", new ExcuterA());
        excuters.put("b", new ExcuterB());
    }

    @Override
        public void excute(String command) {
        excuters.get(command).excute("执行操作");
    }
}
