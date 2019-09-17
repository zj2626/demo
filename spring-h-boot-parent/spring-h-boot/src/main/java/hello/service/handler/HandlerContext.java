package hello.service.handler;

import hello.service.strategy.AbstractOptionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

public class HandlerContext {
    private ApplicationContext applicationContext;
    private Map<String, Class> handleMap;
    
    public HandlerContext(ApplicationContext applicationContext, Map<String, Class> handleMap) {
        this.applicationContext = applicationContext;
        this.handleMap = handleMap;
    }
    
    public AbstractOptionStrategy getInstance(String name) {
        Class clazz = handleMap.get(name);
        if (null == clazz) {
            throw new RuntimeException("传错喽~");
        }
        
        return (AbstractOptionStrategy) applicationContext.getBean(clazz);
    }
}
