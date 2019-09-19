package com.demo.common.service.spring.start;

import com.demo.common.service.spring.start.bean.MyBeanDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ApplicationContext 构造方法:
 * 1.加载资源XML
 * 2.创建BeanFactory, 解析XML文件到BeanDifinition, 注册到BeanFactory中
 * 3.初始化BeanFactory,对Bean(Singleton且非LazyInit非抽象的Bean)进行创建
 */
public class ApplicationContextTest {
    
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:framework/spring.xml");
        
        applicationContext.start();
        MyBeanDao myBeanDao = (MyBeanDao) applicationContext.getBean("myBeanDao");
        
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                myBeanDao.exec();
            });
        }
        
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
