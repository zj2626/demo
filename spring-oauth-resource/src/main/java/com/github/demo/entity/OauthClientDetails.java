package com.github.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class OauthClientDetails {
    @Id
    @Column(length = 250)
    private String clientId;

    @Column(length = 256)
    private String resourceIds;

    @Column(length = 256)
    private String clientSecret;

    @Column(length = 256)
    private String scope;

    @Column(length = 256)
    private String authorizedGrantTypes;

    @Column(length = 256)
    private String webServerRedirectUri;

    @Column(length = 256)
    private String authorities;

    @Column(length = 11)
    private Integer accessTokenValidity;

    @Column(length = 11)
    private Integer refreshTokenValidity;

    @Column(length = 4096)
    private String additionalInformation;

    @Column(length = 256)
    private String autoapprove;

}
