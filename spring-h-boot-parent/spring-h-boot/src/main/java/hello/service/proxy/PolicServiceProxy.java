package hello.service.proxy;

import hello.service.LogService;
import hello.util.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicServiceProxy<P, R, L> {
    Logger logger = LoggerFactory.getLogger(PolicServiceProxy.class);

    private LogService logService;

    private PolicyService<P, R> service;

    private P p;

    private L l;

    public PolicServiceProxy(PolicyService<P, R> service, P param, L logParam) {
        this.logService = SpringBeanUtils.getBean(LogServiceImpl.class);
        this.service = service;
        this.p = param;
        this.l = logParam;
    }

    public R executeProxy() {
        R result = null;

        try {
            result = service.execute(p);
        } catch (Exception e) {
            logService.addErrorLog(l);
        } finally {
            logService.addLog(l);
        }

        return result;
    }
}
