package hello.service.strategy;

import hello.service.handler.HandlerType;
import org.springframework.stereotype.Service;

@HandlerType("delete")
@Service("deleteService")
public class DeleteServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Delete way";
    }
}
