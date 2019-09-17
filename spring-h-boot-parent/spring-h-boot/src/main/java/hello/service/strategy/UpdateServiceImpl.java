package hello.service.strategy;

import org.springframework.stereotype.Service;

@Service("updateService")
public class UpdateServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Update way";
    }
}
