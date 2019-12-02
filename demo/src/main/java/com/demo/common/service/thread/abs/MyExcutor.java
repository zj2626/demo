package com.demo.common.service.thread.abs;

import java.util.Map;

public abstract class MyExcutor {
    protected ThreadDemo threadExcutor;
    
    public abstract String doExcute(Map<String, String> parameter) throws Exception;

    public String doExcuteRead(Map<String, String> ignore) throws Exception {
        return "error";
    }
}
