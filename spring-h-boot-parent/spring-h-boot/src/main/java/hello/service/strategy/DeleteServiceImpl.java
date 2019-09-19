package hello.service.strategy;

import hello.service.handler.HandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@HandlerType("delete")
@Service("deleteService")
public class DeleteServiceImpl extends AbstractOptionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(DeleteServiceImpl.class);
    
    @Override
    public String option() {
        String name = "Delete way";
        logger.info("类型 {}, 线程 {}, 参数 {} IN ~~~", name, Thread.currentThread().getName(), getParam());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("类型 {}, 线程 {}, 参数 {} OUT ~~~", name, Thread.currentThread().getName(), getParam());
        
        return name;
    }
}
