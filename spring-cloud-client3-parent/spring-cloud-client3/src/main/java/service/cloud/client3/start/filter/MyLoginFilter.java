package service.cloud.client3.start.filter;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyLoginFilter extends GenericFilterBean {
    private RequestMatcher requestMatcher;

    public MyLoginFilter() {
        requestMatcher = new AntPathRequestMatcher("/process", "POST");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("AAAA");
        if (requestMatcher.matches((HttpServletRequest) request)) {
            System.out.println("BBBB");
        }
        chain.doFilter(request, response);
    }
}
