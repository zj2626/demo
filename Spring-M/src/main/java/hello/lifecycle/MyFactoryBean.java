package hello.lifecycle;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        System.out.println("[MyFactoryBean] getObject");
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        System.out.println("[MyFactoryBean] getObjectType");
        return null;
    }

    @Override
    public boolean isSingleton() {
        System.out.println("[MyFactoryBean] isSingleton");
        return false;
    }
}
