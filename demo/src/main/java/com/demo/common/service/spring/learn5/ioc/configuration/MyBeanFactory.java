package com.demo.common.service.spring.learn5.ioc.configuration;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyBeanFactory {
    private String xmlPath;

    // key = beanName; value = Bean对象
    Map<String, Object> beanMap = new HashMap<>(10);


    public MyBeanFactory(String xmlPath) {
        this.xmlPath = xmlPath;
        try {
            getBean(getRootElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    public <T> T getBean(Class<T> beanType) {
        return null;
    }


    public Map getBeans() {
        return beanMap;
    }

    /**
     * *. 得到 所有的bean
     * *. 判断有么有default-autowire
     * *. 判断bean标签有木有子标签properties或者constructor-arg
     * *.
     *
     * @return
     * @throws DocumentException
     */
    private Map<String, Object> getBean(Element rootElement) throws Exception {
        List<Element> beanElementList = rootElement.elements("bean");
        Iterator<Element> elementIterator = beanElementList.iterator();
        for (; elementIterator.hasNext(); ) {
            Element element = elementIterator.next();
            // 获得id和class
            String id = element.attributeValue("id");
            String clazzName = element.attributeValue("class");
            // 生成的Class对象,用于反射生成真正的类的对象
            Class classObject = null;
            // 生成的真正的类的对象
            Object object = null;
            try {
                classObject = Class.forName(clazzName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("找不到对应的类", e);
            }
            if (null == classObject) {
                throw new RuntimeException("找不到对应的类2");
            }

            // 判断有没有构造方法, 有则使用构造器进行反射 没有就直接使用Class反射生成对象
            List<Element> constructorElementList = element.elements("constructor-arg");
            if (CollectionUtils.isNotEmpty(constructorElementList)) {
                // TODO 暂时只得到第一个 ???
                Element constructorElement = constructorElementList.get(0);
                // 获得构造方法标签的name和ref
                String setterName = constructorElement.attributeValue("name");
                String refClass = constructorElement.attributeValue("ref");
                // 从生成的beanMap(容器)中获得对应的ref的对象进行赋值
                Object refObject = beanMap.get(refClass);
                if (null == refObject) {
                    throw new RuntimeException("找不到对应的类3");
                }
                // 得到类的构造方法 反射创建对应的对象 TODO 暂时只得到第一个 ???
                Constructor constructor = classObject.getConstructors()[0];
                try {
                    object = constructor.newInstance(refObject);
                } catch (Exception e) {
                    throw new RuntimeException("构建对象失败", e);
                }
            } else {
                try {
                    object = classObject.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("构建对象失败", e);
                }
            }

            // 获得通过setter方法注入的属性 进行注入
            List<Element> propertyElementList = element.elements("property");
            if (CollectionUtils.isNotEmpty(propertyElementList)) {
                Iterator<Element> propertyElementIterator = propertyElementList.iterator();
                for (; propertyElementIterator.hasNext(); ) {
                    Element propertyElement = propertyElementIterator.next();
                    String setterName = propertyElement.attributeValue("name");
                    Object setterValue = propertyElement.attributeValue("value");
                    String refClass = propertyElement.attributeValue("ref");
                    Object value = setterValue;
                    if (StringUtils.isNotEmpty(refClass)) {
                        // 从生成的beanMap(容器)中获得对应的ref的对象进行赋值
                        value = beanMap.get(refClass);
                        if (null == value) {
                            throw new RuntimeException("找不到对应的类4");
                        }
                    }
                    try {
                        Field field = classObject.getDeclaredField(setterName);
                        field.setAccessible(true); // TODO ???
                        String fieldType = field.getType().getName();
                        switch (fieldType) {
                            case "java.lang.Integer":
                                field.set(object, Integer.valueOf(value + ""));
                                break;
                            case "java.lang.Double":
                                field.set(object, Double.valueOf(value + ""));
                                break;
                            default:
                                field.set(object, value);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("找不到对应的方法", e);
                    }
                }
            }

            if (null != object) {
                beanMap.put(id, object);
            }
        }

        return beanMap;
    }

    private Element getRootElement() throws DocumentException {
        String path = this.getClass().getResource("/").getPath() + xmlPath;
        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        if (null != document) {
            return document.getRootElement();
        }
        throw new RuntimeException("莫得根目录");
    }

    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
