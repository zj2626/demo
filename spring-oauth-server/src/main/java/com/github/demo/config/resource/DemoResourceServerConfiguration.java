//package com.github.demo.config.resource;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//import static com.github.demo.utils.OAuth2ServerKey.RESOURCE_ID;
//
///**
// * // mobile resource
// * MOBILE 资源的访问权限配置
// */
//@Configuration
//@EnableResourceServer
//public class DemoResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId(RESOURCE_ID).stateless(false);
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                // Since we want the protected resources to be accessible in the UI as well we need
//                // session creation to be allowed (it's disabled by default in 2.0.6)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .and()
//                // 所有以 /m/  开头的 URL属于此资源
//                .requestMatchers().antMatchers("/demo/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/demo/**").access("#oauth2.hasScope('read') and hasRole('DEMO')");
//
//    }
//}