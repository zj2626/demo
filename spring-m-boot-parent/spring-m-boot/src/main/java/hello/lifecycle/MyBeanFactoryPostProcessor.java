package hello.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("[MyBeanFactoryPostProcessor] 这是BeanFactoryPostProcessor实现类构造器！！");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        System.out.println("[MyBeanFactoryPostProcessor] 调用postProcessBeanFactory方法 || " + beanFactory.getBeanNamesIterator());
        BeanDefinition bd = beanFactory.getBeanDefinition("beanLevel");
        bd.getPropertyValues().addPropertyValue("phone", "999");
    }

}