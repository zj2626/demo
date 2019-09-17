package hello.service.strategy;

import org.springframework.stereotype.Service;

@Service("createService")
public class CreateServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Create way";
    }
}
