package service.cloud.client3.start.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import service.cloud.client3.start.config.WebSecurityConfig;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * 继承AbstractSecurityInterceptor
 * 目的就是为了覆写obtainSecurityMetadataSource方法(由beforeInvocation调用, 通过obtainSecurityMetadataSource().getAttributes(object)获得对应url的权限),
 * 最终调用accessDecisionManager.decide进行验权(如果没有查询到url的权限, 则不会调用到这里)
 */
@Component
public class CustomerPermissionFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private CustomerPermissionDataSource permissionDataSource;
    @Autowired
    private CustomerPermissionAccessDecisionManager permissionAccessDecisionManager;

    @PostConstruct
    public void postConstruct() {
        super.setAccessDecisionManager(permissionAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CustomerFilterInvocation fi = new CustomerFilterInvocation(request, response, chain);

        /**
         * 白名单的请求, 用户是匿名用户(经过AnonymousAuthenticationFilter设置的, 这样后面的filter就不需要进行特殊处理)
         */
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : WebSecurityConfig.ignoreUrl) {
            if (pathMatcher.match(path, fi.getRequest().getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                System.out.println(Thread.currentThread().getName() + " BBBB > 白名单: " + fi.getRequest().getRequestURI());
                return;
            }
        }

        System.out.println(Thread.currentThread().getName() + " BBBB > 需要验证: " + fi.getRequest().getRequestURI());
        // 调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + " BBBB > 异常: " + fi.getRequest().getRequestURI() + " | " + e.getMessage());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        // 调用beforeInvocation的参数必须是FilterInvocation或其子类的对象
        return FilterInvocation.class;
        //        return CustomerFilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return permissionDataSource;
    }
}
