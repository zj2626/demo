package com.github.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

// 配置授权中心信息
@Configuration
@EnableAuthorizationServer // 开启认证授权中心
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    // @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    // @Autowired
    // private UserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        // return new InMemoryTokenStore(); //使用内存中的 token store
        return new JdbcTokenStore(dataSource); /// 使用Jdbctoken store
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 添加授权用户
        clients.jdbc(dataSource).withClient("client_1").secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")// 允许授权范围
                .redirectUris("http://www.baidu.com").authorities("ROLE_ADMIN", "ROLE_USER")// 客户端可以使用的权限
                .scopes("all").accessTokenValiditySeconds(7200).refreshTokenValiditySeconds(7200);
        // 自动往数据插入该应用id信息
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService());// 必须设置
        // UserDetailsService
        // 否则刷新token 时会报错
    }

    // 已经讲过UserDetailsService 写读数据
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("user_1").password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        userDetailsService.createUser(User.withUsername("user_2")
                .password(new BCryptPasswordEncoder().encode("1234567")).authorities("ROLE_USER").build());
        return userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();// 允许表单登录

    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return daoAuhthenticationProvider().authenticate(authentication);
            }
        };
    }

    @Bean
    public AuthenticationProvider daoAuhthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 加密方式
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
    // 6个git 关于oauth2 五个是 1.5版本 1个 2.0
}
