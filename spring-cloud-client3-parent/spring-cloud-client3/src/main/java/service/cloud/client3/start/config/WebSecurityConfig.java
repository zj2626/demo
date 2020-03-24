package service.cloud.client3.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.cloud.client3.start.data.Database;
import service.cloud.client3.start.utils.MyPasswordEncoder;

import java.util.Set;

//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private Database database;

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

        registry.antMatchers("/login", "/signup", "/api/**").permitAll();
        Set<String> urls = database.getUrls();
        for (String url : urls) {
            String permission = database.getPermission(url);
            registry.antMatchers(url).hasAnyAuthority(permission);
        }

        registry.anyRequest().authenticated().and()

                // 允许用户进行基于表单的认证-指定登录页的路径-允许所有用户访问登录页
                .formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failureHandler).permitAll().and()
                .csrf().disable()

                // 登出
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("cookie_Names")
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
