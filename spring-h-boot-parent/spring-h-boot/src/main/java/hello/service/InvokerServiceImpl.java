package hello.service;

import hello.service.handler.HandlerContext;
import hello.service.model.MethodEnum;
import hello.service.model.OptionParam;
import hello.service.strategy.AbstractOptionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 调用策略接口(OptionStrategy)的调用者
 */
@Service
public class InvokerServiceImpl implements InvokerService {
    private static final Logger logger = LoggerFactory.getLogger(InvokerServiceImpl.class);
    
    @Autowired
    private HandlerContext context;
    
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
            logger.info("调用的是 {} - {}", methodType.getCommand(), methodType.getDesc());
            
            AbstractOptionStrategy option = context.getInstance(methodType.getCommand());
            option.setParam(new OptionParam(methodType.getDesc(), methodType.getCommand().hashCode()));
            result = option.invoke();
        } else {
            throw new RuntimeException("传错啦~~~~~~~~~~~");
        }
        
        logger.info("最终结果: ~ + {}", result);
        
        return result;
    }
    
    /*
    * 原始方法: 使用switch+case
    
    
    @Autowired
    @Qualifier("createService")
    private AbstractOptionStrategy createService;
    
    @Autowired
    @Qualifier("updateService")
    private AbstractOptionStrategy updateService;
    
    @Autowired
    @Qualifier("deleteService")
    private AbstractOptionStrategy deleteService;
    
    @Override
    public String invoke(String type) {
        String result = null;
        
        MethodEnum methodType = MethodEnum.get(type);
        if (null != methodType) {
            AbstractOptionStrategy option = null;
            
            logger.info("调用的是 {} - {}", methodType.getCommand(), methodType.getDesc());
            switch (methodType) {
                case CREATE:
                    option = createService;
                    break;
                case UPDATE:
                    option = updateService;
                    break;
                case DELETE:
                    option = deleteService;
                    break;
                default:
                    logger.error("传错喽~");
            }
            result = option.invoke();
        } else {
            throw new RuntimeException("传错啦~~~~~~~~~~~");
        }
        
        logger.info("最终结果: ~ + {}", result);
        
        return result;
    }
    */
}
