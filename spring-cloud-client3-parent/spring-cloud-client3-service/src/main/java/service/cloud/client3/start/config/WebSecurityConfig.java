package service.cloud.client3.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import service.cloud.client3.start.data.Database;
import service.cloud.client3.start.filter.*;
import service.cloud.client3.start.utils.MyPasswordEncoder;

import java.util.Set;

/**
 * EnableGlobalMethodSecurity 参数:
 * 1. prePostEnabled: 开启了基于表达式的方法安全控制 @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
 * 2. securedEnabled: 开启了角色注解 @Secured
 * 3. jsr250Enabled:  启用 JSR-250 安全控制注解 @DenyAll @PermitAll @RolesAllowed
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private Database database;
    @Autowired
    private CustomerLoginFilter myLoginFilter;
    @Autowired
    private CustomerPermissionFilter myPermissionFilter;
    @Autowired
    private CustomLogoutHandler logoutHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private MyAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    public static String[] ignoreUrl =
            ("/swagger-ui.html,/swagger-resources/**,/swagger/**,/**/springfox-swagger-ui/**," +
                    "/**/v2/api-docs," +
                    "/v2/api-docs" +
                    "/**/*.js,/**/*.css,/**/*.ico,/**/*.png," +
                    "/login,/doLogin,/signup,/api/**")
                    .split(",", 99);

    /**
     * 配置用户认证信息+权限
     * 配置认证管理器 AuthenticationManager
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    /**
     * 安全过滤器链配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        // ignoreUrl地址不会验证登录状态 但是还是会经过filter
        registry.antMatchers(ignoreUrl).permitAll();
        Set<String> urls = database.getUrls();
        for (String url : urls) {
            String permission = database.getPermission(url);
            registry.antMatchers(url).hasAnyAuthority(permission);
        }

        registry.and().authorizeRequests().anyRequest().authenticated()

                // 允许用户进行基于表单的认证-指定登录页的路径-允许所有用户访问登录页
                //                .and().formLogin().loginPage("/login")
                //                .successHandler(successHandler).failureHandler(failureHandler).permitAll()
                //                .and().httpBasic()

                // 登出, 即使不设置,也有默认的LogoutFilter执行, 其中有一个默认的handler(SecurityContextLogoutHandler)进行退出登录处理: 执行SecurityContextHolder.clearContext();
                //                .and().logout().logoutUrl("/logout")
                //                .addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutSuccessHandler)

                /*
                 * AuthenticationEntryPoint 该类用来统一处理 AuthenticationException (401 错误 - 未授权) 异常
                 * AccessDeniedHandler 该类用来统一处理 AccessDeniedException (403 错误 - 被禁止)异常
                 */
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                // 防跨站点攻击
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .and().anonymous().disable()
        ;

        /**
         * 默认过滤器之间的order距离都是100
         * addFilterBefore, 会把自定义过滤器插入过滤器链中, 顺序是第二个参数指定的过滤器的前, (排序用的order-1)
         * addFilterBefore(A, B.class) 给A的序号比B小1，addFilterAfter(A, B.class) 给A的序号比B大1
         */
        // 用户登录信息验证
        registry.and().addFilterBefore(myLoginFilter, UsernamePasswordAuthenticationFilter.class);
        // 用户权限信息验证
        registry.and().addFilterBefore(myPermissionFilter, FilterSecurityInterceptor.class);
        ;
    }

    /**
     * 核心过滤器配置 一般不会过多来自定义
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    //    @Autowired
    //    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //        auth
    //                .inMemoryAuthentication()
    //                .passwordEncoder(new BCryptPasswordEncoder())
    //                // home add view delete
    //                .withUser("admin").password(new BCryptPasswordEncoder().encode("111111")).roles("ADMIN").and()
    //                // home add view
    //                .withUser("user").password(new BCryptPasswordEncoder().encode("111111")).roles("USER").and()
    //                // home
    //                .withUser("guest").password(new BCryptPasswordEncoder().encode("guest")).roles("GUEST").and()
    //        ;
    //
    //    }

    //    @Bean
    //    public SpringAuthenticationProvider springAuthenticationProvider() {
    //        return new SpringAuthenticationProvider();
    //    }
    //
    //    @Bean
    //    public SpringDataUserDetailsService springDataUserDetailsService() {
    //        return new SpringDataUserDetailsService();
    //    }
}
