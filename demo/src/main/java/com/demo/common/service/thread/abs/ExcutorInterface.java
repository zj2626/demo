package com.demo.common.service.thread.abs;

import java.util.Map;

public interface ExcutorInterface {

    Object doExcute(Map<String, Object> parameter) throws Exception;

    Object doExcuteRead(Map<String, Object> ignore) throws Exception;
}
