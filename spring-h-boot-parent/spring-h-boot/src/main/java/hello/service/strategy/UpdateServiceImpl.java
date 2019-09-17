package hello.service.strategy;

import hello.service.handler.HandlerType;
import org.springframework.stereotype.Service;

@HandlerType("update")
@Service("updateService")
public class UpdateServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Update way";
    }
}
