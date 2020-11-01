package com.github.demo.controller;

import com.github.demo.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shengzhao Li
 */
@RestController
@RequestMapping("/u1/")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private OAuth2ClientProperties oauth2ClientProperties;

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    /**
     * 可以直接调用
     * http://localhost:8080/oauth/token?client_id=503a3e789d75435b8e06ca38e37ac0ad&client_secret=h3nDh4ykyFs86buRARTPwFWGWLvDNZhm&grant_type=password&scope=read&username=admin&password=admin
     * 或者调用此/login方法
     *
     * @param loginDto
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     * @author zj2626
     * @date 2020/9/1
     */
    @PostMapping("/login")
    public OAuth2AccessToken login(@RequestBody LoginDto loginDto) {
        LOG.info("u1 login {}", accessTokenUri);
        // 创建 ClientCredentialsResourceDetails 对象
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername(loginDto.getLoginPhone());
        resourceDetails.setPassword(loginDto.getPassword());
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(oauth2ClientProperties.getClientId());
        resourceDetails.setClientSecret(oauth2ClientProperties.getClientSecret());
        // 创建 OAuth2RestTemplate 对象
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        // 获取访问令牌
        return restTemplate.getAccessToken();
    }

    @PostMapping("info")
    public String info() {
        return "info";
    }
}