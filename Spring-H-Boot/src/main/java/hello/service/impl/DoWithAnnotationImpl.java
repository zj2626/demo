package hello.service.impl;

import hello.service.DoWithAnnotation;
import org.springframework.stereotype.Service;

@Service("doWithAnnotationImpl")
@com.alibaba.dubbo.config.annotation.Service(group = "zj.hello-app", filter = "mySecondFilter,myOtherFilter")
public class DoWithAnnotationImpl implements DoWithAnnotation {

    @Override
    public String sayFuck(String name) {
        System.out.println("doHSomethingImpl sayFuck " + name);
        return "fuck u " + name;
    }
}
