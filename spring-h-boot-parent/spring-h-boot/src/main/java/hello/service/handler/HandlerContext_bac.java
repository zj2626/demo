//package hello.service.handler;
//
//import hello.service.strategy.AbstractOptionStrategy;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class HandlerContext {
//    private ApplicationContext applicationContext;
//    private Map<String, Class> handleMap;
//
//    public void setHandleMap(Map<String, Class> handleMap) {
//        this.handleMap = handleMap;
//    }
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    public AbstractOptionStrategy getInstance(String name) {
//        Class clazz = handleMap.get(name);
//        if (null == clazz) {
//            clazz = DefaultServiceImpl.class;
//        }
//
//        return (AbstractOptionStrategy) applicationContext.getBean(clazz);
//    }
//}
