package hello.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("[MyBeanPostProcessor] 这是BeanPostProcessor实现类构造器！！\n");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("[MyBeanPostProcessor                  ] 调用postProcessBeforeInitialization对属性进行更改！Before > " + beanName);
//        System.out.println(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("[MyBeanPostProcessor                  ] 调用postProcessAfterInitialization对属性进行更改！  After < " + beanName + '\n');
//        System.out.println(bean);
        return bean;
    }
}