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
        logger.info("线程 {}, 类型 {}, 参数 {}, 调用时间 {} IN ~~~",
                Thread.currentThread().getName(),
                name,
                getParam().getName(),
                getParam().getTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("线程 {}, 类型 {}, 参数 {}, 调用时间 {} OUT ~~~", Thread.currentThread().getName(),
                name,
                getParam().getName(),
                getParam().getTime());
        
        return name;
    }
}
