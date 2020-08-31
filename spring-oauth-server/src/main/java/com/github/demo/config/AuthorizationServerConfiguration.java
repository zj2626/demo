package com.github.demo.config;

import com.github.demo.domain.oauth.CustomJdbcClientDetailsService;
import com.github.demo.service.OauthService;
import com.github.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;


    @Autowired
    private ClientDetailsService clientDetailsService;


    @Autowired
    private OauthService oauthService;


    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;


    @Autowired
    private UserService userDetailsService;


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    //        /*
    //         * JDBC TokenStore
    //         */
    //        @Bean
    //        public TokenStore tokenStore(DataSource dataSource) {
    //            return new JdbcTokenStore(dataSource);
    //        }

    /*
     * Redis TokenStore (有Redis场景时使用)
     */
    //        @Bean
    //        public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
    //            final RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
    //            //prefix
    //            redisTokenStore.setPrefix(RESOURCE_ID);
    //            return redisTokenStore;
    //        }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // 令牌读写服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .tokenServices(tokenServices)
                .authorizationCodeServices(authorizationCodeServices)
                .userDetailsService(userDetailsService)
                .userApprovalHandler(userApprovalHandler())
                .authenticationManager(authenticationManager)
        ;
    }

    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        return new CustomJdbcClientDetailsService(dataSource);
    }

    // 客户端读写服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    // 用来配置令牌端点的安全约束,也就是这个端点谁能访问，谁不能访问
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // real 值可自定义
        oauthServer.realm("spring-oauth-server")
                // 支持 client_credentials 的配置 (允许表单认证)
                .allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
        ;
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public UserApprovalHandler userApprovalHandler() {
        OauthUserApprovalHandler userApprovalHandler = new OauthUserApprovalHandler();
        userApprovalHandler.setOauthService(oauthService);
        userApprovalHandler.setTokenStore(tokenStore);
        userApprovalHandler.setClientDetailsService(this.clientDetailsService);
        userApprovalHandler.setRequestFactory(oAuth2RequestFactory());
        return userApprovalHandler;
    }

}