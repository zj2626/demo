package hello.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import hello.service.DoWithAnnotation;
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
