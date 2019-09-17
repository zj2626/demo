package hello.service.strategy;

import hello.service.handler.HandlerType;
import org.springframework.stereotype.Service;

@HandlerType("create")
@Service("createService")
public class CreateServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Create way";
    }
}
