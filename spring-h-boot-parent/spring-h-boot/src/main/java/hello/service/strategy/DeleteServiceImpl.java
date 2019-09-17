package hello.service.strategy;

import org.springframework.stereotype.Service;

@Service("deleteService")
public class DeleteServiceImpl extends AbstractOptionStrategy {
    @Override
    public String option() {
        return "Delete way";
    }
}
