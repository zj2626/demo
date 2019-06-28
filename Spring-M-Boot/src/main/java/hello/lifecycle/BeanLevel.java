package hello.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * @author zhangj
 * @version $Id: Person.java, v 0.1 2019/2/28 11:04 zhangj Exp $
 * <p>
 * 1、Bean自身的方法　　：　　这个包括了Bean本身调用的方法和通过配置文件中<bean>的init-method和destroy-method指定的方法
 * <p>
 * 2、Bean级生命周期接口方法　　：　　这个包括了BeanNameAware、BeanFactoryAware、InitializingBean和DisposableBean这些接口的方法
 * <p>
 * 3、容器级生命周期接口方法　　：　　这个包括了InstantiationAwareBeanPostProcessor 和 BeanPostProcessor 这两个接口实现，一般称它们的实现类为“后处理器”。
 * <p>
 * 4、工厂后处理器接口方法　　：　　这个包括了AspectJWeavingEnabler, ConfigurationClassPostProcessor, CustomAutowireConfigurer等等非常有用的工厂后处理器　　接口的方法。工厂后处理器也是容器级的。在应用上下文装配配置文件之后立即调用。
 */
public class BeanLevel implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    private String address;
    private int phone;

    public BeanLevel() {
        System.out.println("【构造器】调用构造器实例化");
    }

    public void initMethod(){
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    public void destroyMethod(){
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("【注入属性】注入属性name");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        System.out.println("【注入属性】注入属性address");
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
        System.out.println("【注入属性】注入属性phone");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"address\":\"")
                .append(address).append('\"');
        sb.append(",\"phone\":")
                .append(phone);
        sb.append('}');
        return sb.toString();
    }
}
