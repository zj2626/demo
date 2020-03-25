package service.cloud.client3.start.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import service.cloud.client3.start.data.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class CustomerPermissionDataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private Database database;

    /**
     * 根据提供的受保护对象的信息，其实就是URI，获取该URI 配置的所有权限
     *
     * @param object FilterInvocation的实现对象, 封装了请求响应信息
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        CustomerFilterInvocation filterInvocation = (CustomerFilterInvocation) object;
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        PathMatcher pathMatcher = new AntPathMatcher();

        // 查询得到请求路径
        String path = filterInvocation.getRequestUrl();
        // 查询得到对应的权限
        Map<String, ConfigAttribute> configAttributeMap = database.getUrlPermission();
        System.out.println(Thread.currentThread().getName() + " CCCC > 访问路径的所有权限: " + configAttributeMap);
        for (Map.Entry<String, ConfigAttribute> entry : configAttributeMap.entrySet()) {
            if (pathMatcher.match(entry.getKey(), path)) {
                configAttributes.add(entry.getValue());
            }
        }
        if (CollectionUtils.isEmpty(configAttributes)) {
            System.out.println(Thread.currentThread().getName() + " CCCC > 访问路径没有权限设置");
        }
        return configAttributes;
    }

    /**
     * 获取全部权限
     *
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 对特定的安全对象是否提供 ConfigAttribute 支持
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
