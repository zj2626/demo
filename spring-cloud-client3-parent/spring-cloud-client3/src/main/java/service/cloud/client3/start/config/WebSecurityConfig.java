package service.cloud.client3.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // https://blog.csdn.net/qq_22172133/article/details/86503223
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 每个macher按照他们的声明顺序执行
                // 任何用户都可以访问的URL
                .antMatchers("/resources/**", "/signup", "/about").permitAll()
                // 拥有ADMIN角色的用户可以访问的URL
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 同时拥有ADMIN和DBA角色的用户可以访问的URL
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                // 其他的所有请求都需要用户被认证
                .anyRequest().authenticated().and()

                // 允许用户进行基于表单的认证
                //                .formLogin().and()
                // 允许用户进行基于表单的认证-指定登录页的路径-允许所有用户访问登录页
                .formLogin().loginPage("/login").permitAll().and()
                // 允许用户使用HTTP基于验证进行认证
                .httpBasic().and()

                // 登出
                .logout()
                // 登出URL
                .logoutUrl("/logout")
                // 注销之后跳转的URL
                .logoutSuccessUrl("/hello")
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user").password(new BCryptPasswordEncoder().encode("111111")).roles("USER").and()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("111111")).roles("ADMIN").and()
        ;

    }

    //    @Bean
    //    public SpringAuthenticationProvider springAuthenticationProvider() {
    //        return new SpringAuthenticationProvider();
    //    }
    //
    //    @Bean
    //    public SpringDataUserDetailsService springDataUserDetailsService() {
    //        return new SpringDataUserDetailsService();
    //    }
    //
    //    @Bean
    //    public BCryptPasswordEncoder passwordEncoder() {
    //        return new BCryptPasswordEncoder();
    //    }
}
