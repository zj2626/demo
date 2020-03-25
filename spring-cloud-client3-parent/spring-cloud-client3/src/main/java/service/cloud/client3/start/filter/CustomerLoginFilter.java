package service.cloud.client3.start.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import service.cloud.client3.start.data.Database;
import service.cloud.client3.start.data.MyUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomerLoginFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher;

    @Autowired
    private Database database;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerLoginFilter() {
        requestMatcher = new AntPathRequestMatcher("/doLogin", "POST");
    }

    /**
     * 注释掉SecurityConfig中的formLogin()之后 用户的登录信息验证需要手动在此完成
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("\n" +
                Thread.currentThread().getName() + " AAAA > 请求地址: " + request.getRequestURI() + " --[ " + header + " ]");
        System.out.println(
                Thread.currentThread().getName() + " AAAA > 用户信息: " + (SecurityContextHolder.getContext().getAuthentication()));

        // 验证登录信息 + 保存登录信息
        if (requestMatcher.matches(request) && null == SecurityContextHolder.getContext().getAuthentication()) {
            // 验证登录信息
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String databasePassword = database.getPasswordByName(username);
            if (passwordEncoder.encode(password).equals(databasePassword)) {
                // 保存登录信息
                MyUser myUser = database.getUserInfoByName(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 放入安全上下文中
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
