package hello.service.strategy;

import hello.service.handler.HandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@HandlerType("default")
@Service("defaultService")
public class DefaultServiceImpl extends AbstractOptionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceImpl.class);
    
    @Override
    public String option() {
        return null;
    }
}
