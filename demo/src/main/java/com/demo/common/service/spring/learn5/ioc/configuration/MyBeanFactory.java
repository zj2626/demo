package com.demo.common.service.spring.learn5.ioc.configuration;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MyBeanFactory {
    private String xmlPath;

    public MyBeanFactory(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Object getBean(String beanName) {
        try {
            return getBean(getRootElement());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(Class<T> beanType) {
        return null;
    }

    /**
     * *. 得到 所有的bean
     * *. 判断有么有default-autowire
     * *. 判断bean标签有木有子标签properties或者constructor-arg
     * *.
     * @return
     * @throws DocumentException
     */
    private Element getBean(Element rootElement) throws DocumentException {

        return null;
    }

    private Element getRootElement() throws DocumentException {
        String path = this.getClass().getResource("/").getPath() + xmlPath;
        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        if(null != document){
            return document.getRootElement();
        }
        throw new RuntimeException("莫得根目录");
    }
}
