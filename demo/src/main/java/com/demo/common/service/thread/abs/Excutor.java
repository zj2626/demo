package com.demo.common.service.thread.abs;

import java.util.Map;

public abstract class Excutor {
    protected ThreadDemo threadExcutor;
    
    public abstract String doExcute(Map<String, String> parameter) throws Exception;
}