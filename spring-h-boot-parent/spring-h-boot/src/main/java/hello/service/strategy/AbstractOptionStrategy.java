package hello.service.strategy;

import hello.service.model.OptionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOptionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractOptionStrategy.class);
    
    private OptionParam param;
    
    public OptionParam getParam() {
        return param;
    }
    
    public void setParam(OptionParam param) {
        this.param = param;
    }
    
    public String invoke() {
        String result = option();
        
        common(result);
        
        return result;
    }
    
    /**
     * 各个子类继承并实现的接口,具体的业务逻辑内容
     *
     * @return
     */
    public abstract String option();
    
    protected void common(String result) {
        logger.info("公共方法, 提供给自身或者各个子类调用 " + result);
    }
}
