package com.kdniao.logisticsfront.common.biz.service.impl.spring.start;

import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.MyBean;
import com.kdniao.logisticsfront.common.biz.service.impl.spring.start.bean.MyBeanDao;
import org.junit.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 1.BeanFactory[BeanFactory]
 *
 */
public class BeanFactoryTest {
    /**
     * bean的创建
     */
    @Test
    public void beanCreate() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        MyBean myBean = factory.createBean(MyBean.class);
        System.out.println(myBean);

        // 并没有存储在缓存, 所以这样getBean不到
//        factory.getBean("myBean");
    }

    /**
     * bean的存储
     */
    @Test
    public void beanStore() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        MyBean bean = factory.createBean(MyBean.class);
        // 存储 单例
        factory.registerSingleton("myBean", bean);

        MyBean myBean = (MyBean) factory.getBean("myBean");
        System.out.println(myBean);

        // 存储 单例 (其属性MyBean并没有注入该对象)
        factory.registerSingleton("myBeanDao", new MyBeanDao());
        System.out.println(factory.getBean("myBeanDao"));
    }

    /**
     * 依赖关系自动注入
     */
    @Test
    public void beanDependency() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        /**
         * 多了两个参数:autowireMode表示使用什么形式对依赖进行注入, dependencyCheck代表是否检查依赖
         */
        MyBean myBean = (MyBean) factory.createBean(MyBean.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        factory.registerSingleton("myBean", myBean);
        System.out.println(myBean);

        // 创建该bean时会把依赖的MyBean注入到这个bean,如果找不到 就会报错
        MyBeanDao myBeanDao = (MyBeanDao) factory.createBean(MyBeanDao.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        factory.registerSingleton("myBeanDao", myBeanDao);

        System.out.println("myBeanDao" + myBeanDao);
    }

}
