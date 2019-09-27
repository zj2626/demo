package hello.service.handler;

import hello.service.strategy.AbstractOptionStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HandlerContext {
    @Autowired
    private List<AbstractOptionStrategy> handleList;

    @Autowired
    private AbstractOptionStrategy defaultService;

    public AbstractOptionStrategy getInstance(String name) {
        AbstractOptionStrategy clazz = handleList.stream()
                .filter(f ->
                        StringUtils.equals(name, f.getClass().getAnnotation(HandlerType.class).value()))
                .findFirst()
                .orElse(defaultService);

        return clazz;
    }
}
