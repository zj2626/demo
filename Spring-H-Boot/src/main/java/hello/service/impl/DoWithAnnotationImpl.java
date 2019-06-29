package hello.service.impl;

import hello.service.DoWithAnnotation;
import org.springframework.stereotype.Service;

@Service
@com.alibaba.dubbo.config.annotation.Service(group = "${dubbo.provider.group}", filter = "${dubbo.provider.filter}")
public class DoWithAnnotationImpl implements DoWithAnnotation {

    @Override
    public String sayFuck(String name) {
        System.out.println("doHSomethingImpl sayFuck " + name);
        return "fuck u " + name;
    }
}
