package service.cloud.client.start.service.fallback;

import org.springframework.stereotype.Component;
import service.cloud.client.start.service.InterfaceFeignService;

/**
 * 熔断以后执行这里的方法
 */
@Component
public class SchedualServiceHiHystric implements InterfaceFeignService {
    @Override
    public String fun(String name) {
        return "fallback to here method[fun]";
    }

    @Override
    public String fun2(Boolean success) {
        return "fallback to here method[fun2]";
    }

    @Override
    public String doServiceRequestHi(String name) {
        return "fallback to here method[doServiceRequestHi]";
    }

    @Override
    public String doServiceRequestHalfFailed(Boolean success) {
        return "fallback to here method[doServiceRequestHalfFailed]";
    }
}
