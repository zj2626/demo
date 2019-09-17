package hello.service.handler;

import com.google.common.collect.Maps;
import jodd.io.findfile.ClassScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {
    
    private String packageDir = "hello.service.strategy";
    
    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * 扫描@HandlerType注解
     *
     * @param beanFactory bean工厂
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("扫描@HandlerType注解 开始");
        //  TODO newHashMapWithExpectedSize ???
        Map<String, Class> handleMap = Maps.newHashMapWithExpectedSize(3);
        
        // 扫描有@HandlerType注解的类 把扫描到的类放入handleMap中
        ClassScanner scanner = new ClassScanner();
//        scanner.onEntry(entryData -> {
//            // process entry data
//        }).scan(packageDir).start();
        
        System.out.println("扫描@HandlerType注解 完成");
        
        // 向spring中注册一个bean
        HandlerContext context = new HandlerContext(applicationContext, handleMap);
        beanFactory.registerSingleton(HandlerContext.class.getSimpleName(), context);
    }
}
