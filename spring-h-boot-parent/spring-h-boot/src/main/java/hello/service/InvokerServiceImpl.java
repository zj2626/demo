package hello.service;

import hello.service.model.MethodEnum;
import hello.service.strategy.AbstractOptionStrategy;
import hello.service.strategy.CreateServiceImpl;
import hello.service.strategy.DeleteServiceImpl;
import hello.service.strategy.UpdateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 调用策略接口(OptionStrategy)的调用者
 */
@Service
public class InvokerServiceImpl implements InvokerService {
    private static final Logger logger = LoggerFactory.getLogger(InvokerServiceImpl.class);
    
    /**
     * 调用策略接口 根据传入参数判断调用哪个service
     *
     * @param type 包含三种(create, update, delete)类型, 分别调用不同的service
     */
    @Override
    public String invoke(String type) {
        String result = null;
        
        MethodEnum methodType = MethodEnum.get(type);
        if (null != methodType) {
            AbstractOptionStrategy option = null;
            
            logger.info("调用的是 {} - {}", methodType.getCommand(), methodType.getDesc());
            switch (methodType) {
                case CREATE:
                    option = new CreateServiceImpl();
                    break;
                case UPDATE:
                    option = new UpdateServiceImpl();
                    break;
                case DELETE:
                    option = new DeleteServiceImpl();
                    break;
                default:
                    logger.error("传错喽~");
            }
            
            if (null != option) {
                result = option.invoke();
            }
        } else {
            throw new RuntimeException("传错啦~~~~~~~~~~~");
        }
    
        logger.info("最终结果: ~ + {}", result);
        
        return result;
    }
}
