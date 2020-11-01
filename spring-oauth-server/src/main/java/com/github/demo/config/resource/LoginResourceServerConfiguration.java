package com.github.demo.config.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static com.github.demo.utils.OAuth2ServerKey.RESOURCE_ID;

/**
 * // unity resource
 * UNITY 资源的访问权限配置
 */
@Configuration
@EnableResourceServer
public class LoginResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                // 所有以 /unity/  开头的 URL属于此资源
                .requestMatchers().antMatchers("/u1/**")
                .and()
                .authorizeRequests()
                .antMatchers("/u1/**").permitAll();
    }

}