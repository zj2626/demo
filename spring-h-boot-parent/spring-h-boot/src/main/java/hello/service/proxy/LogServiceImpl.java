package hello.service.proxy;

import hello.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    @Override
    public String addErrorLog(Object msg) {
        return null;
    }

    @Override
    public String addLog(Object msg) {
        return null;
    }
}
