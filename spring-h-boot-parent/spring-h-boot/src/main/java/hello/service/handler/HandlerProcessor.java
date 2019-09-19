package hello.service.handler;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HandlerProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(HandlerProcessor.class);
    
    private static final ClassLoader CLASS_LOADER = ClassLoader.getSystemClassLoader();
    private static final String packageDir = "classpath*:hello/service/strategy/*";
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HandlerProcessor.applicationContext = applicationContext;
    }
    
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
        
        try {
            doScan(handleMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("扫描@HandlerType注解 完成 : " + handleMap.size());
        
        // 向spring中bean设置属性
        HandlerContext context = beanFactory.getBean(HandlerContext.class);
        context.setApplicationContext(applicationContext);
        context.setHandleMap(handleMap);
    }
    
    private void doScan(Map<String, Class> handleMap) throws IOException, ClassNotFoundException {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        //这里特别注意一下类路径必须这样写
        //获取指定包下的所有类
        Resource[] resources = resourcePatternResolver.getResources(packageDir);
        
        MetadataReaderFactory metadata = new SimpleMetadataReaderFactory();
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadata.getMetadataReader(resource);
            ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
            sbd.setResource(resource);
            sbd.setSource(resource);
            candidates.add(sbd);
        }
        
        for (BeanDefinition beanDefinition : candidates) {
            String classname = beanDefinition.getBeanClassName();
            //扫描HandlerType注解
            HandlerType component = Class.forName(classname).getAnnotation(HandlerType.class);
            if (component != null) {
                Class clazz = CLASS_LOADER.loadClass(classname);
                
                handleMap.put(component.value(), clazz);
            }
        }
    }
}
