package hello.service;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Service(group = "${dubbo.provider.group}")
public class DoWithAnnotationImpl implements DoWithAnnotation {
    private static final Logger logger = LoggerFactory.getLogger(DoWithAnnotationImpl.class);

    public DoWithAnnotationImpl() {
        logger.info("构造造 DoWithAnnotationImpl");
    }
    
    @Override
    public String sayFuck(String name) {
        System.out.println("<<方法调用>> [doHSomethingImpl] " + name + ", 执行时间: " + LocalDateTime.now());
        return "fuck u " + name;
    }
}
