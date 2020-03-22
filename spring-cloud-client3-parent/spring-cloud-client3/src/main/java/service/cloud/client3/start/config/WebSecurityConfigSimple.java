package service.cloud.client3.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * security认证的两种模式:
 * 1. formLogin: 表单提交认证模式
 * 2. httpBasic: 浏览器与服务器作认证授权
 */
//@EnableWebSecurity
public class WebSecurityConfigSimple extends WebSecurityConfigurerAdapter {
    // https://blog.csdn.net/qq_22172133/article/details/86503223

    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;

    /**
     * 配置用户认证信息何权限
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 简单写法: 用户信息+权限
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                // home add view delete
                .withUser("admin").password(passwordEncoder().encode("111111"))
                .authorities("home", "user", "add", "delete", "db").and()
                // home add view
                .withUser("user").password(passwordEncoder().encode("111111"))
                .authorities("home", "user", "add", "otherA").and()
                // home
                .withUser("guest").password(passwordEncoder().encode("guest"))
                .authorities("home").and()
                // home add view db
                .withUser("dba").password(passwordEncoder().encode("111111"))
                .authorities("home", "user", "db").and()
        ;
    }

    /**
     * 配置拦截请求资源
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 每个macher按照他们的声明顺序执行
                // 任何用户都可以访问的URL
                .antMatchers("/login", "/signup", "/api/**").permitAll()
                // 同时拥有两个角色的用户可以访问的URL
                .antMatchers("/home").hasAnyAuthority("home")
                .antMatchers("/user").hasAnyAuthority("user")
                .antMatchers("/user/add").hasAnyAuthority("add")
                .antMatchers("/admin/delete").hasAnyAuthority("delete")
                .antMatchers("/db/**").hasAnyAuthority("db")
                // 其他的所有请求都需要用户被认证
                .anyRequest().authenticated().and()
                //                .antMatchers("/**").fullyAuthenticated().and()

                // 允许用户进行基于表单的认证-指定登录页的路径-允许所有用户访问登录页
                .formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failureHandler).permitAll().and()
                // 防跨站点攻击
                .csrf().disable()
                // 允许用户使用HTTP基于验证进行认证
                //                .httpBasic().and()

                // 登出
                .logout()
                // 登出URL
                .logoutUrl("/logout")
                // 注销之后跳转的URL
                .logoutSuccessUrl("/login")
                // 设置定制的 LogoutSuccessHandler。如果指定了这个选项那么logoutSuccessUrl()的设置会被忽略
                //                .logoutSuccessHandler(logoutSuccessHandler)
                // 否在注销时让HttpSession无效 默认true
                .invalidateHttpSession(true)
                // 添加一个LogoutHandler; 默认SecurityContextLogoutHandler会被添加为最后一个LogoutHandler
                //                .addLogoutHandler(logoutHandler)
                // 要移除的cookie
                .deleteCookies("cookie_Names")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
