package hello.service.impl;

import hello.service.DoWithAnnotation;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Service(group = "${dubbo.provider.group}")
public class DoWithAnnotationImpl implements DoWithAnnotation {
    
    @Override
    public String sayFuck(String name) {
        System.out.println("<<方法调用>> [doHSomethingImpl] " + name + ", 执行时间: " + LocalDateTime.now());
        return "fuck u " + name;
    }
}
