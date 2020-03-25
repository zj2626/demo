package service.cloud.client3.start.filter;

import org.springframework.security.web.FilterInvocation;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomerFilterInvocation extends FilterInvocation {
    public CustomerFilterInvocation(ServletRequest request, ServletResponse response, FilterChain chain) {
        super(request, response, chain);
    }
}
